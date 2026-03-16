package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.config.AuthFacade;
import com.isaac.br.selfcheckoutmercado.dto.CartItemDTO;
import com.isaac.br.selfcheckoutmercado.dto.CheckoutResponseDTO;
import com.isaac.br.selfcheckoutmercado.dto.ResponseCartItem;
import com.isaac.br.selfcheckoutmercado.enums.PriceType;
import com.isaac.br.selfcheckoutmercado.enums.Status;
import com.isaac.br.selfcheckoutmercado.exceptions.*;
import com.isaac.br.selfcheckoutmercado.model.CartItem;
import com.isaac.br.selfcheckoutmercado.model.CheckoutSession;
import com.isaac.br.selfcheckoutmercado.model.Product;
import com.isaac.br.selfcheckoutmercado.repository.SessionRepository;
import com.isaac.br.selfcheckoutmercado.service.CartItemService;
import com.isaac.br.selfcheckoutmercado.service.CheckoutService;
import com.isaac.br.selfcheckoutmercado.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CheckoutServiceImp implements CheckoutService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private AudiService audiService;

    @Autowired
    private AuthFacade authFacade;

    @Override
    public CheckoutResponseDTO createCheckout() {

        var checkout = new CheckoutSession(Status.OPEN, LocalDateTime.now(),0.0);
        try {
            CheckoutSession session = sessionRepository.save(checkout);
            return new CheckoutResponseDTO(
                    session.getId(),
                    session.getStatus(),
                    session.getCreatAt(),
                    session.getTotalAmount());

        }catch (Exception e){
            throw new InternalServerException("Erro ao iniciar checkout");
        }
    }

    @Override
    public void cancelCheckout(long checkoutId) {

        if (!cartItemService.findBySessionId(checkoutId).isEmpty()) {
            throw new CartAllItemsException("Impossivel cancelar a checkout");
        }

        CheckoutSession session = getSessionById(checkoutId);
        session.setStatus(Status.CANCELLED);
        try {
            sessionRepository.delete(session);
            audiService.log(authFacade.getEmployeeId(),authFacade.getTerminalId(),"CANCELLED");
        }catch (Exception e){
            throw new InternalServerException("Erro ao deletar checkout");
        }
    }

    @Transactional
    @Override
    public ResponseCartItem addItemToCart(Long idSession, CartItemDTO dto) {
        var checkoutSession = getSessionById(idSession);
        Product product = productService.getBarCode(dto.barcode());
        CartItem cartItem = new CartItem();
        double subTotal;

        if (product.getPriceType() == PriceType.WEIGHT){
            if (dto.weight() == 0) {
                throw new QuantityInvalidException("Informe o peso para este produto");
            }
            BigDecimal pesoKg = new BigDecimal(dto.weight()).divide(new BigDecimal(1000));
            BigDecimal total = pesoKg.multiply(BigDecimal.valueOf(product.getPrice()));
                cartItem.setWeight(pesoKg.doubleValue());
                product.setQuantity((int) (product.getQuantity() - dto.weight()));
                subTotal = total.doubleValue();
        }else{
            if (dto.quantity() == 0){
            throw new QuantityInvalidException("Quantity invalid");
            }

            product.setQuantity(product.getQuantity() - dto.quantity());
            subTotal = dto.quantity() * product.getPrice();
            cartItem.setQuantity(dto.quantity());
        }

        cartItem.setSubtotal(subTotal);
        BigDecimal calcAmount = BigDecimal.valueOf(checkoutSession.getTotalAmount()).add(BigDecimal.valueOf(subTotal));

        checkoutSession.setTotalAmount(calcAmount.doubleValue());
        cartItem.setSessionId(checkoutSession.getId());
        cartItem.setProductId(product);
        try {
            sessionRepository.save(checkoutSession);
            cartItemService.saveCartItem(cartItem);
            productService.saveProduct(product);
            return new ResponseCartItem(product.getId(),product.getName(), subTotal,calcAmount.doubleValue());

        }catch (Exception e){
            throw new InternalServerException("Erro ao salvar produto");
        }
    }

    @Transactional
    @Override
    public void removeItemFromCart(long idCart,long idSession,long productId) {
        Product product = productService.getProduct(productId);
        var checkout = getSessionById(idSession);
        var car = cartItemService.getItemById(idCart, checkout.getId(), product);

        if (product.getPriceType() == PriceType.WEIGHT){
            double sessionAmountGrams = checkout.getTotalAmount() - (product.getPrice() * car.getWeight());
            checkout.setTotalAmount(sessionAmountGrams);
            int weightInGrams = (int) (car.getWeight() * 1000);
            product.setQuantity(product.getQuantity() + weightInGrams);
        }else{
            double sessionAmountQuanti = checkout.getTotalAmount() - (product.getPrice() * car.getQuantity());
            checkout.setTotalAmount(sessionAmountQuanti);
            product.setQuantity(product.getQuantity() + car.getQuantity());
        }

        try {
            cartItemService.deleteCartItem(car);
            productService.saveProduct(product);
            sessionRepository.save(checkout);
        }catch (Exception e){
            throw new InternalServerException("Erro ao remover produto");
        }

    }

    @Override
    public CheckoutSession getSessionById(Long idSession) {
        CheckoutSession checkoutSession = sessionRepository.findById(Math.toIntExact(idSession))
                .orElseThrow(() -> new NotFoundException("Session not found"));

        if (!checkoutSession.getStatus().equals(Status.OPEN)) {
            throw new NegadoException("Session closed");
        }

        return checkoutSession;
    }
}

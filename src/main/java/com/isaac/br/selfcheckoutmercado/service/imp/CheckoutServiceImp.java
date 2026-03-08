package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.config.AuthFacade;
import com.isaac.br.selfcheckoutmercado.dto.CartItemDTO;
import com.isaac.br.selfcheckoutmercado.dto.CheckoutResponseDTO;
import com.isaac.br.selfcheckoutmercado.dto.ResponseCartItem;
import com.isaac.br.selfcheckoutmercado.enums.Status;
import com.isaac.br.selfcheckoutmercado.exceptions.NegadoException;
import com.isaac.br.selfcheckoutmercado.exceptions.NotFoundException;
import com.isaac.br.selfcheckoutmercado.model.CartItem;
import com.isaac.br.selfcheckoutmercado.model.CheckoutSession;
import com.isaac.br.selfcheckoutmercado.model.Product;
import com.isaac.br.selfcheckoutmercado.repository.SessionRepository;
import com.isaac.br.selfcheckoutmercado.service.CartItemService;
import com.isaac.br.selfcheckoutmercado.service.CheckoutService;
import com.isaac.br.selfcheckoutmercado.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
            throw new RuntimeException("Erro ao salvar checkout");
        }
    }

    @Override
    public void cancelCheckout(long checkoutId) {
        CheckoutSession session = getSessionById(checkoutId);
        session.setStatus(Status.CANCELLED);
        try {
            sessionRepository.delete(session);
            audiService.log(authFacade.getEmployeeId(),authFacade.getTerminalId(),"CANCELLED");
        }catch (Exception e){
            throw new RuntimeException("Erro ao deletar checkout");
        }
    }

    @Override
    public ResponseCartItem addItemToCart(Long idSession, CartItemDTO dto) {
        var checkoutSession = getSessionById(idSession);
        Product product = productService.getBarCode(dto.barcode());
        double calc = 0;
        if (dto.weight() != 0){
            calc = dto.weight() * product.getPrice();
        }else{
            calc = dto.quantity() * product.getPrice();
        }

        double calcAmount = checkoutSession.getTotalAmount() + calc;
        checkoutSession.setTotalAmount(calcAmount);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(dto.quantity());
        cartItem.setSubtotal((product.getPrice()*dto.quantity()));
        cartItem.setSessionId(checkoutSession.getId());
        cartItem.setProductId(product);
        try {
            sessionRepository.save(checkoutSession);
            cartItemService.saveCartItem(cartItem);
            return new ResponseCartItem(product.getId(),product.getName(), calc,calcAmount);

        }catch (Exception e){
            throw new RuntimeException("Erro ao salvar checkout");
        }
    }

    @Override
    public void removeItemFromCart(long idCart,long idSession,long productId) {
        Product product = productService.getProduct(productId);
        var checkout = getSessionById(idSession);
        var car = cartItemService.getItemById(idCart, checkout.getId(), product);
        double sessionAmount = checkout.getTotalAmount() - (product.getPrice() * car.getQuantity());
        checkout.setTotalAmount(sessionAmount);
        cartItemService.deleteCartItem(car);
        sessionRepository.save(checkout);



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

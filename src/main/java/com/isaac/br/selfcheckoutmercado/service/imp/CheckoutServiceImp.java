package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.dto.CartItemDTO;
import com.isaac.br.selfcheckoutmercado.dto.CheckoutResponseDTO;
import com.isaac.br.selfcheckoutmercado.dto.ResponseCartItem;
import com.isaac.br.selfcheckoutmercado.enums.Status;
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
        Optional<CheckoutSession> session = sessionRepository.findById((int) checkoutId);
        if(session.isEmpty()){
            throw new NotFoundException("Não encontrado");
        }
        try {
            sessionRepository.delete(session.get());
        }catch (Exception e){
            throw new RuntimeException("Erro ao deletar checkout");
        }
    }

    @Override
    public ResponseCartItem addItemToCart(Long idSession, CartItemDTO dto) {
        Optional<CheckoutSession> checkoutSession = sessionRepository.findById(Math.toIntExact(idSession));
        if(checkoutSession.isEmpty()){
            throw new NotFoundException("It was not possible to find checkout");
        }
        Product product = productService.getBarCode(dto.barcode());
        double calc = 0;
        if (dto.weight() != 0){
            calc = dto.weight() * product.getPrice();
        }else{
            calc = dto.quantity() * product.getPrice();
        }

        double calcAmount = checkoutSession.get().getTotalAmount() + calc;
        checkoutSession.get().setTotalAmount(calcAmount);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(dto.quantity());
        cartItem.setSubtotal(calcAmount);
        cartItem.setSessionId(checkoutSession.get().getId());
        cartItem.setProductId(product);
        try {
            sessionRepository.save(checkoutSession.get());
            cartItemService.saveCartItem(cartItem);
            return new ResponseCartItem(product.getId(),product.getName(), calc,calcAmount);

        }catch (Exception e){
            throw new RuntimeException("Erro ao salvar checkout");
        }
    }
}

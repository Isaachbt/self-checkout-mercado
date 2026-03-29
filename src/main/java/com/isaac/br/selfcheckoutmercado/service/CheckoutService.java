package com.isaac.br.selfcheckoutmercado.service;

import com.isaac.br.selfcheckoutmercado.dto.CartItemDTO;
import com.isaac.br.selfcheckoutmercado.dto.CheckoutResponseDTO;
import com.isaac.br.selfcheckoutmercado.dto.ResponseCartItem;
import com.isaac.br.selfcheckoutmercado.model.CheckoutSession;

import java.util.UUID;

public interface CheckoutService {

    CheckoutResponseDTO createCheckout();
    void cancelCheckout(long checkoutId, UUID logId);
    ResponseCartItem addItemToCart(Long idSession, CartItemDTO dto);
    void removeItemFromCart(long idCart,long idSession,long productId);
    CheckoutSession getSessionById(Long idSession);
}
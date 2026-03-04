package com.isaac.br.selfcheckoutmercado.service;

import com.isaac.br.selfcheckoutmercado.dto.CartItemDTO;
import com.isaac.br.selfcheckoutmercado.dto.CheckoutResponseDTO;
import com.isaac.br.selfcheckoutmercado.dto.ResponseCartItem;

public interface CheckoutService {

    CheckoutResponseDTO createCheckout();
    void cancelCheckout(long checkoutId);
    ResponseCartItem addItemToCart(Long idSession, CartItemDTO dto);
}
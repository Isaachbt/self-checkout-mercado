package com.isaac.br.selfcheckoutmercado.service;

import com.isaac.br.selfcheckoutmercado.model.CartItem;
import com.isaac.br.selfcheckoutmercado.model.Product;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

    List<CartItem> getAllCartItems();
    void saveCartItem(CartItem cartItem);
    CartItem getItemById(long sessionId, long id, Product productId);
    void  deleteCartItem(CartItem cartItem);
    List<CartItem> findBySessionId(long sessionId);
}

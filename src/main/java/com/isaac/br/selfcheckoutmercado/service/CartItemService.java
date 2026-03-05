package com.isaac.br.selfcheckoutmercado.service;

import com.isaac.br.selfcheckoutmercado.model.CartItem;

import java.util.List;

public interface CartItemService {

    List<CartItem> getAllCartItems();
    void saveCartItem(CartItem cartItem);
    void getItemById(long sessionId,long id,long productId);
}

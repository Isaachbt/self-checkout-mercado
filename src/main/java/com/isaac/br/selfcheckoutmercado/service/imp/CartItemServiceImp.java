package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.exceptions.NotFoundException;
import com.isaac.br.selfcheckoutmercado.model.CartItem;
import com.isaac.br.selfcheckoutmercado.model.Product;
import com.isaac.br.selfcheckoutmercado.repository.CartItemRepository;
import com.isaac.br.selfcheckoutmercado.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImp implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> getAllCartItems() {
        return List.of();
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem getItemById(long id,long sessionId, Product productId) {
        Optional<CartItem> cartItem = cartItemRepository.findByIdAndSessionIdAndProductId(id,sessionId,productId);
        if (cartItem.isEmpty()){
            throw new NotFoundException("CartItem not found");
        }
        return cartItem.get();
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);

    }


}

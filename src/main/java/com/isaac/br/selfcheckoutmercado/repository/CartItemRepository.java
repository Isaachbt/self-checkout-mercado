package com.isaac.br.selfcheckoutmercado.repository;

import com.isaac.br.selfcheckoutmercado.model.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
}

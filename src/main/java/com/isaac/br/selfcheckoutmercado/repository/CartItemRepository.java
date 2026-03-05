package com.isaac.br.selfcheckoutmercado.repository;

import com.isaac.br.selfcheckoutmercado.model.CartItem;
import com.isaac.br.selfcheckoutmercado.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {

    Optional<CartItem> findByIdAndSessionIdAndProductId(Long id, Long sessionId, Product productId);
}

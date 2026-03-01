package com.isaac.br.selfcheckoutmercado.repository;

import com.isaac.br.selfcheckoutmercado.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepository extends JpaRepository<Product,Integer> {

}

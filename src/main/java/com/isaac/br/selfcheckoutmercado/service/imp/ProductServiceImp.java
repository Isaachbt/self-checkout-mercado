package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.exceptions.NotFoundException;
import com.isaac.br.selfcheckoutmercado.model.Product;
import com.isaac.br.selfcheckoutmercado.repository.ProductRepository;
import com.isaac.br.selfcheckoutmercado.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getBarCode(String barCode) {
        Optional<Product> optional = productRepository.findByBarCode(barCode);
        if (optional.isEmpty()) {
            throw new NotFoundException("Item não encontrado.");
        }
        return optional.get();
    }
}

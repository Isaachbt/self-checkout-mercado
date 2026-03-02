package com.isaac.br.selfcheckoutmercado.service;

import com.isaac.br.selfcheckoutmercado.model.Product;

public interface ProductService {

    Product getBarCode(String barCode);
}

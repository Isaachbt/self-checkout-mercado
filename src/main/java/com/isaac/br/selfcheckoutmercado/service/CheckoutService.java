package com.isaac.br.selfcheckoutmercado.service;

import com.isaac.br.selfcheckoutmercado.dto.CheckoutDTO;
import com.isaac.br.selfcheckoutmercado.dto.CheckoutResponseDTO;

public interface CheckoutService {

    CheckoutResponseDTO createCheckout();
    void cancelCheckout(long checkoutId);

}
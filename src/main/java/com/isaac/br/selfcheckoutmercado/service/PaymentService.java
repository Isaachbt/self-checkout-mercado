package com.isaac.br.selfcheckoutmercado.service;

import com.isaac.br.selfcheckoutmercado.dto.MethodPaymentDTO;
import com.isaac.br.selfcheckoutmercado.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse processPayment(long idSession, MethodPaymentDTO paymentDto);

}

package com.isaac.br.selfcheckoutmercado.dto;

import com.isaac.br.selfcheckoutmercado.enums.MethodPayment;
import com.isaac.br.selfcheckoutmercado.enums.StatusPayment;

public record PaymentResponse(long paymentId, double amount, StatusPayment statusPayment, MethodPayment methodPayment) {
}

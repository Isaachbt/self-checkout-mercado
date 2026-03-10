package com.isaac.br.selfcheckoutmercado.dto;

import com.isaac.br.selfcheckoutmercado.enums.MethodPayment;
import jakarta.validation.constraints.NotNull;

public record MethodPaymentDTO(@NotNull(message = "Insira uma forma de pagamento") MethodPayment methodPayment) {
}

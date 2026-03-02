package com.isaac.br.selfcheckoutmercado.dto;

import com.isaac.br.selfcheckoutmercado.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CheckoutResponseDTO(@NotBlank Long id, @NotNull Status status, @NotNull LocalDateTime createdAt, @NotNull double totalAmount) {
}

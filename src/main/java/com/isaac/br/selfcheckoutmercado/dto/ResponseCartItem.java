package com.isaac.br.selfcheckoutmercado.dto;

import jakarta.validation.constraints.NotNull;

public record ResponseCartItem(@NotNull long id, @NotNull String product, double subtotal, @NotNull double sessionTotal) {
}

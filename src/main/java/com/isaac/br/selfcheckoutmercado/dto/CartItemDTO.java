package com.isaac.br.selfcheckoutmercado.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CartItemDTO(@NotNull(message = "Codigo de barra necessario") @Size(min = 13,max = 13) String barcode,
                          @NotNull(message = "Quantidade necessaria")@Size(min = 1) int quantity, double weight) {

}

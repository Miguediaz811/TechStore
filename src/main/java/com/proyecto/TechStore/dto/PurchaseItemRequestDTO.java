package com.proyecto.TechStore.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PurchaseItemRequestDTO {

    @NotNull(message = "El id del producto es obligatorio")
    private Long productId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    private Integer quantity;
}
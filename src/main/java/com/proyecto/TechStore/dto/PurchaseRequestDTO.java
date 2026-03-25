package com.proyecto.TechStore.dto;

import java.util.List;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PurchaseRequestDTO {
    
    @NotNull(message = "La lista de items es obligatoria")
    @Size(min = 1, message = "Debe haber al menos un item en la compra")
    private List<PurchaseItemRequestDTO> items;
}

package com.proyecto.TechStore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}

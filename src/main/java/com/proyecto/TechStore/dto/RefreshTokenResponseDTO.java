package com.proyecto.TechStore.dto;

import lombok.Data;

@Data
public class RefreshTokenResponseDTO {
    private String message;
    private String jwt;
}

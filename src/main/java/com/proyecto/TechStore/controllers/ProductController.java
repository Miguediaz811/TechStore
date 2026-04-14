package com.proyecto.TechStore.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.TechStore.dto.MessageResponseDTO;
import com.proyecto.TechStore.dto.ProductRequestDTO;
import com.proyecto.TechStore.entity.Product;
import com.proyecto.TechStore.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO request, @RequestAttribute("role") String role) {
        try {
            if (!"admin".equals(role)) {
                MessageResponseDTO error = new MessageResponseDTO();
                error.setMessage("No tienes permisos para realizar esta acción");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO request, @RequestAttribute("role") String role) {
        try {
            if (!"admin".equals(role)) {
                MessageResponseDTO error = new MessageResponseDTO();
                error.setMessage("No tienes permisos para realizar esta acción");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            }
            return ResponseEntity.ok(productService.updateProduct(id, request));
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteProduct(@PathVariable Long id, @RequestAttribute("role") String role) {
        try {
            if (!"admin".equals(role)) {
                MessageResponseDTO error = new MessageResponseDTO();
                error.setMessage("No tienes permisos para realizar esta acción");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            }
            return ResponseEntity.ok(productService.deleteProduct(id));
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
package com.proyecto.TechStore.controllers;

import com.proyecto.TechStore.dto.MessageResponseDTO;
import com.proyecto.TechStore.dto.PurchaseRequestDTO;
import com.proyecto.TechStore.entity.Purchase;
import com.proyecto.TechStore.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> createPurchase(@Valid @RequestBody PurchaseRequestDTO request, @RequestAttribute("role") String role, @RequestAttribute("id") Long userId) {
        try {
            if (!"client".equals(role)) {
                MessageResponseDTO error = new MessageResponseDTO();
                error.setMessage("Solo los clientes pueden realizar compras");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.createPurchase(request, userId));
        } catch (Exception e) {
            e.printStackTrace();
            MessageResponseDTO error = new MessageResponseDTO();
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/my-history")
    public ResponseEntity<List<Purchase>> getMyPurchases(@RequestAttribute("id") Long userId) {
        try {
            return ResponseEntity.ok(purchaseService.getMyPurchases(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAllPurchases(@RequestAttribute("role") String role) {
        try {
            if (!"admin".equals(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            return ResponseEntity.ok(purchaseService.getAllPurchases());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
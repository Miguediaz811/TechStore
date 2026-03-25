package com.proyecto.TechStore.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.TechStore.dto.MessageResponseDTO;
import com.proyecto.TechStore.dto.PurchaseItemRequestDTO;
import com.proyecto.TechStore.dto.PurchaseRequestDTO;
import com.proyecto.TechStore.entity.Product;
import com.proyecto.TechStore.entity.Purchase;
import com.proyecto.TechStore.entity.PurchaseItem;
import com.proyecto.TechStore.repository.ProductRepository;
import com.proyecto.TechStore.repository.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    
    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    @Transactional
    public MessageResponseDTO createPurchase(PurchaseRequestDTO request, Long UserId) {
        Purchase purchase = new Purchase();
        purchase.setUserId(UserId);
        purchase.setPurchaseDate(LocalDateTime.now());

        List<PurchaseItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (PurchaseItemRequestDTO itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + itemReq.getProductId()));

            if (product.getStock() < itemReq.getQuantity()) {
                throw new RuntimeException(
                    "Stock insuficiente para: " + product.getName() + " | Disponible " + product.getStock());
            }

            product.setStock(product.getStock() - itemReq.getQuantity());
            productRepository.save(product);

            PurchaseItem item = new PurchaseItem();
            item.setPurchase(purchase);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(product.getPrice());
            items.add(item);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }

        purchase.setItems(items);
        purchase.setTotal(total);
        purchaseRepository.save(purchase);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Compra realizada eitosamente. Total: $" + total);
        return response;
    }

    public List<Purchase> getMyPurchases(Long userId) {
        return purchaseRepository.findByUserId(userId);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
}

package com.proyecto.TechStore.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.proyecto.TechStore.dto.MessageResponseDTO;
import com.proyecto.TechStore.dto.ProductRequestDTO;
import com.proyecto.TechStore.entity.Product;
import com.proyecto.TechStore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public MessageResponseDTO createProduct(ProductRequestDTO request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());

        productRepository.save(product);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Producto creado exitosamente");
        return response;
    }

    public MessageResponseDTO updateProduct(Long id, ProductRequestDTO request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());

        productRepository.save(product);

        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Producto actualizado exitosamente");
        return response;
    }

    public MessageResponseDTO deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productRepository.deleteById(id);
        MessageResponseDTO response = new MessageResponseDTO();
        response.setMessage("Producto eliminado exitosamente");
        return response;
    }
}

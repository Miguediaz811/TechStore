package com.proyecto.TechStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.proyecto.TechStore.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}

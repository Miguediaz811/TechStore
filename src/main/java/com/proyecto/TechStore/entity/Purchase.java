package com.proyecto.TechStore.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Guardamos solo el userId igual que tu Users guarda rolId como Long
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "total")
    private BigDecimal total;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PurchaseItem> items;
}
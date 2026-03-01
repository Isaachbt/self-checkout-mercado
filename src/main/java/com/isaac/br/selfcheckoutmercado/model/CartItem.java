package com.isaac.br.selfcheckoutmercado.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CartItem")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false)
    private long sessionId;
    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private Product productId;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private double weight;
    @Column(nullable = false)
    private double subtotal;
}

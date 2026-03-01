package com.isaac.br.selfcheckoutmercado.model;

import com.isaac.br.selfcheckoutmercado.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "CheckoutSession")
public class CheckoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private LocalDateTime creatAt;
    @Column(nullable = false)
    private double totalAmount;
}
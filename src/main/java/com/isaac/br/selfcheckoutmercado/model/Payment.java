package com.isaac.br.selfcheckoutmercado.model;

import com.isaac.br.selfcheckoutmercado.enums.MethodPayment;
import com.isaac.br.selfcheckoutmercado.enums.StatusPayment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private long sessionId;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPayment statusPayment;
    @Enumerated(EnumType.STRING)
    private MethodPayment methodPayment;
}

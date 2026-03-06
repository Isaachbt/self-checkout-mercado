package com.isaac.br.selfcheckoutmercado.model;

import com.isaac.br.selfcheckoutmercado.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private long badgeId;
    private String name;
    @Column(name = "pin_hash")
    private String pinHash;
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean active;
    private int failedAttempts;
    private LocalDateTime lockedUntil;
}

package com.isaac.br.selfcheckoutmercado.model;

import com.isaac.br.selfcheckoutmercado.enums.LogAudi;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "audiLog")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID employeeId;
    private UUID terminalId;
    @Enumerated(EnumType.STRING)
    private LogAudi action;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;

    public AuditLog() {
    }

    public AuditLog(UUID employeeId, UUID terminalId, LogAudi action, LocalDateTime opened_at, LocalDateTime closed_at) {
        this.employeeId = employeeId;
        this.terminalId = terminalId;
        this.action = action;
        this.openedAt = opened_at;
        this.closedAt = closed_at;
    }


}

package com.isaac.br.selfcheckoutmercado.repository;

import com.isaac.br.selfcheckoutmercado.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AudiRepository extends JpaRepository<AuditLog, UUID> {
}

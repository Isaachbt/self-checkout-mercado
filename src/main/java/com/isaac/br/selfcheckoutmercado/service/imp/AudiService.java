package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.model.AuditLog;
import com.isaac.br.selfcheckoutmercado.repository.AudiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AudiService {

    @Autowired
    private AudiRepository audiRepository;

    public void log(UUID employeeId, UUID terminalId, String action) {
        AuditLog log = new AuditLog();
        log.setEmployeeId(employeeId);
        log.setTerminalId(terminalId);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        audiRepository.save(log);
    }
}

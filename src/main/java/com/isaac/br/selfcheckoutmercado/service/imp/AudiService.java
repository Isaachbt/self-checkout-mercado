package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.enums.LogAudi;
import com.isaac.br.selfcheckoutmercado.exceptions.NegadoException;
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


    public UUID log(AuditLog audi) {
        audiRepository.save(audi);
        return audi.getId();
    }

    public void closedLog(UUID logId) {
        AuditLog log = audiRepository.findById(logId)
                .orElseThrow(() -> new NegadoException("Log não encontrado"));
        log.setAction(LogAudi.FINISHED);
        log.setClosedAt(LocalDateTime.now());
        audiRepository.save(log);
    }
}

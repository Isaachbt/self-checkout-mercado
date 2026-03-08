package com.isaac.br.selfcheckoutmercado.config;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthFacade {

    public Claims getClaims() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        return (Claims) authentication.getPrincipal();
    }

    public UUID getEmployeeId() {
        return UUID.fromString(getClaims().getSubject());
    }

    public UUID getTerminalId() {
        return UUID.fromString(getClaims().get("terminal", String.class));
    }

    public String getRole() {
        return getClaims().get("role", String.class);
    }
}

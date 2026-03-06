package com.isaac.br.selfcheckoutmercado.controller;

import com.isaac.br.selfcheckoutmercado.dto.LoginDTO;
import com.isaac.br.selfcheckoutmercado.service.imp.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(loginDTO));
    }
}

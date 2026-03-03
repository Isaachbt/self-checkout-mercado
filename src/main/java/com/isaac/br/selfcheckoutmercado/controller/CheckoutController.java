package com.isaac.br.selfcheckoutmercado.controller;

import com.isaac.br.selfcheckoutmercado.dto.CheckoutDTO;
import com.isaac.br.selfcheckoutmercado.dto.CheckoutResponseDTO;
import com.isaac.br.selfcheckoutmercado.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout/")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;


    @PostMapping("sessions")
    public ResponseEntity<CheckoutResponseDTO> createSession(){
        return ResponseEntity.ok(checkoutService.createCheckout());
    }
}

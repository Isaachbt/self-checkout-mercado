package com.isaac.br.selfcheckoutmercado.controller;

import com.isaac.br.selfcheckoutmercado.dto.CheckoutResponseDTO;
import com.isaac.br.selfcheckoutmercado.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout/")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("sessions")
    public ResponseEntity<CheckoutResponseDTO> createSession(){
        return ResponseEntity.ok(checkoutService.createCheckout());
    }

    @PostMapping("sessions/{id}/cancel")
    public ResponseEntity<Object> cancelSession(@PathVariable("id") long checkoutId){
        checkoutService.cancelCheckout(checkoutId);
        return ResponseEntity.status(HttpStatus.OK).body("Volte sempre.");

    }
}

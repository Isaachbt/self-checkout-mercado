package com.isaac.br.selfcheckoutmercado.controller;

import com.isaac.br.selfcheckoutmercado.dto.CartItemDTO;
import com.isaac.br.selfcheckoutmercado.dto.CheckoutResponseDTO;
import com.isaac.br.selfcheckoutmercado.dto.ResponseCartItem;
import com.isaac.br.selfcheckoutmercado.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("checkout/sessions")
    public ResponseEntity<CheckoutResponseDTO> createSession(){
        return ResponseEntity.ok(checkoutService.createCheckout());
    }

    @PostMapping("checkout/sessions/{id}/cancel")
    public ResponseEntity<Object> cancelSession(@PathVariable("id") long checkoutId){
        checkoutService.cancelCheckout(checkoutId);
        return ResponseEntity.status(HttpStatus.OK).body("Volte sempre.");
    }

    @PostMapping("checkout/sessions/{idSession}/items")
    public ResponseEntity<ResponseCartItem> addItemToCart(@PathVariable("idSession") long checkoutId, @RequestBody CartItemDTO item){
        return  ResponseEntity.ok(checkoutService.addItemToCart(checkoutId, item));
    }



}

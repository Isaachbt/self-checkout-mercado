package com.isaac.br.selfcheckoutmercado.controller;

import com.isaac.br.selfcheckoutmercado.dto.MethodPaymentDTO;
import com.isaac.br.selfcheckoutmercado.dto.PaymentResponse;
import com.isaac.br.selfcheckoutmercado.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment/")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("checkout/session/{id}/payments")
    public ResponseEntity<PaymentResponse> pay(@PathVariable("id") long id, @RequestBody MethodPaymentDTO methodPayment){

        return ResponseEntity.ok(paymentService.processPayment(id,methodPayment));

    }


}

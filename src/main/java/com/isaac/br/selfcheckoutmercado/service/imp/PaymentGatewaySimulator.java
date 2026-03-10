package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.enums.StatusPayment;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentGatewaySimulator {

    public StatusPayment processPayment(Double amount) {

        Random random = new Random();
        int result = random.nextInt(100);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Erro ao simular tempo de sleep.");
        }

        if(result < 80) {
            return StatusPayment.APPROVED;
        }

        return StatusPayment.REJECTED;
    }
}

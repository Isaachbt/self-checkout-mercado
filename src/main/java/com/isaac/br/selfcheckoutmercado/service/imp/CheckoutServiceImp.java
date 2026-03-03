package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.dto.CheckoutDTO;
import com.isaac.br.selfcheckoutmercado.dto.CheckoutResponseDTO;
import com.isaac.br.selfcheckoutmercado.enums.Status;
import com.isaac.br.selfcheckoutmercado.model.CheckoutSession;
import com.isaac.br.selfcheckoutmercado.repository.SessionRepository;
import com.isaac.br.selfcheckoutmercado.service.CheckoutService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CheckoutServiceImp implements CheckoutService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public CheckoutResponseDTO createCheckout() {

        var checkout = new CheckoutSession(Status.OPEN, LocalDateTime.now(),0.0);
        try {
            CheckoutSession session = sessionRepository.save(checkout);
            return new CheckoutResponseDTO(
                    session.getId(),
                    session.getStatus(),
                    session.getCreatAt(),
                    session.getTotalAmount());

        }catch (Exception e){
            throw new RuntimeException("Erro ao salvar checkout");
        }
    }
}

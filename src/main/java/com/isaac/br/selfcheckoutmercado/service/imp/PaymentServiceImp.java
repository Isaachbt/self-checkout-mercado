package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.dto.MethodPaymentDTO;
import com.isaac.br.selfcheckoutmercado.dto.PaymentResponse;
import com.isaac.br.selfcheckoutmercado.enums.StatusPayment;
import com.isaac.br.selfcheckoutmercado.exceptions.NegadoException;
import com.isaac.br.selfcheckoutmercado.model.CheckoutSession;
import com.isaac.br.selfcheckoutmercado.model.Payment;
import com.isaac.br.selfcheckoutmercado.repository.CartItemRepository;
import com.isaac.br.selfcheckoutmercado.repository.PaymentRepository;
import com.isaac.br.selfcheckoutmercado.repository.SessionRepository;
import com.isaac.br.selfcheckoutmercado.service.CheckoutService;
import com.isaac.br.selfcheckoutmercado.service.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImp implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private PaymentGatewaySimulator paymentGatewaySimulator;

    @Transactional
    @Override
    public PaymentResponse processPayment(long idSession, MethodPaymentDTO paymentDto) {
        CheckoutSession session = checkoutService.getSessionById(idSession);

        if (session.getTotalAmount() <= 0){
            throw new NegadoException("Impossivel seguir para pagamento.");
        }

        Payment payment = new Payment();
        payment.setSessionId(session.getId());
        payment.setAmount(session.getTotalAmount());
        payment.setStatusPayment(StatusPayment.PENDING);
        payment.setMethodPayment(paymentDto.methodPayment());

        //paymentRepository.save(payment);

        StatusPayment result = paymentGatewaySimulator.processPayment(session.getTotalAmount());

        payment.setStatusPayment(result);

        if(result ==  StatusPayment.APPROVED){

                session.setTotalAmount(0);
                cartItemRepository.deleteAllBySessionId(session.getId());
                sessionRepository.save(session);

        }
        paymentRepository.save(payment);

        return new PaymentResponse(payment.getId(), payment.getAmount(), result,paymentDto.methodPayment());
    }
}

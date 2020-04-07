package br.com.fiap.spring.service;

import br.com.fiap.spring.dto.PaymentRequest;
import br.com.fiap.spring.entity.Payment;

public interface PaymentService {
	Payment processStudentCreditCardPayment(PaymentRequest paymentRequest);
}

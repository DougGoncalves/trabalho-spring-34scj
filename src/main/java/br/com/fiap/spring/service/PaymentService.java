package br.com.fiap.spring.service;

import br.com.fiap.spring.dto.PaymentRequest;
import br.com.fiap.spring.entity.Payment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PaymentService {
	Payment processStudentCreditCardPayment(PaymentRequest paymentRequest);
	List<Payment> getStudentCreditCardPaymentStatement(LocalDateTime startDate, LocalDateTime endDate,
													   String studentRegistration);
}

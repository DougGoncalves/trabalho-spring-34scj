package br.com.fiap.spring.service.impl;

import br.com.fiap.spring.dto.PaymentRequest;
import br.com.fiap.spring.entity.Payment;
import br.com.fiap.spring.enums.PaymentStatus;
import br.com.fiap.spring.repository.PaymentRepository;
import br.com.fiap.spring.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class PaymentServiceImpl implements PaymentService {

	private PaymentRepository paymentRepository;

	@Autowired
	public PaymentServiceImpl(final  PaymentRepository paymentRepository){
		this.paymentRepository = paymentRepository;
	};

	@Override
	public Payment processStudentCreditCardPayment(PaymentRequest paymentRequest){

		PaymentStatus paymentStatus = authorizePaymentForTransaction(
				paymentRequest.getCreditCard().getCardNumber(),
				paymentRequest.getCreditCard().getVerificationCode(),
				paymentRequest.getTotalOrderAmount());

		return paymentRepository.save(new Payment(paymentRequest.getOrderId(), paymentRequest.getStudentId(),
				paymentRequest.getTotalOrderAmount(), paymentStatus));
	}

	private PaymentStatus authorizePaymentForTransaction(String cardNumber, Integer verificationCode,
														 BigDecimal totalOrderAmount){

		if (!validateCreditCardInIssuer(cardNumber, verificationCode))
			return PaymentStatus.ABORTED;

		if(!validateCreditCardTransaction(cardNumber, verificationCode, totalOrderAmount))
			return PaymentStatus.REJECTED;

		if (!isNeededValidateFraudAnalysis(cardNumber))
			return PaymentStatus.PENDING;

		return PaymentStatus.APPROVED;
	}

	private Boolean validateCreditCardInIssuer(String cardNumber, Integer verificationCode){
		return new Random().nextBoolean();
	}

	private Boolean validateCreditCardTransaction(String cardNumber, Integer verificationCode,
												  BigDecimal totalOrderAmount){
		return new Random().nextBoolean();
	}

	private Boolean isNeededValidateFraudAnalysis(String cardNumber){
		return new Random().nextBoolean();
	}
}

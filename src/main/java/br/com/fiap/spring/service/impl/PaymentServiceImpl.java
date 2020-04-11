package br.com.fiap.spring.service.impl;

import br.com.fiap.spring.advice.ResponseError;
import br.com.fiap.spring.advice.exceptions.StudentCreditCardConflictException;
import br.com.fiap.spring.dto.PaymentRequest;
import br.com.fiap.spring.entity.Payment;
import br.com.fiap.spring.entity.StudentCreditCard;
import br.com.fiap.spring.enums.PaymentStatus;
import br.com.fiap.spring.repository.PaymentRepository;
import br.com.fiap.spring.repository.StudentCreditCardRepository;
import br.com.fiap.spring.service.PaymentService;
import br.com.fiap.spring.service.CreditCardValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

	private PaymentRepository paymentRepository;
	private CreditCardValidateService creditCardValidateService;
	private StudentCreditCardRepository studentCreditCardRepository;

	@Autowired
	public PaymentServiceImpl(final  PaymentRepository paymentRepository,
							  final CreditCardValidateService creditCardValidateService,
							  final StudentCreditCardRepository studentCreditCardRepository){
		this.paymentRepository = paymentRepository;
		this.creditCardValidateService = creditCardValidateService;
		this.studentCreditCardRepository = studentCreditCardRepository;
	};

	@Override
	public Payment processStudentCreditCardPayment(PaymentRequest paymentRequest){

		Optional<StudentCreditCard> studentCreditCard = Optional.ofNullable(studentCreditCardRepository
				.findByRegistration(paymentRequest.getStudentRegistration()));

		if (!studentCreditCard.isPresent() ||
				(!studentCreditCard.get().getCardNumber().equals(paymentRequest.getCreditCard().getCardNumber()) ||
				!studentCreditCard.get().getVerificationCode().equals(paymentRequest.getCreditCard().getVerificationCode()))){
			throw new StudentCreditCardConflictException("Há uma inconsitência em relação aos dados enviados e os dados armazenados.");
		}

		PaymentStatus paymentStatus = authorizePaymentForTransaction(
				paymentRequest.getCreditCard().getCardNumber(),
				paymentRequest.getCreditCard().getVerificationCode(),
				paymentRequest.getTotalOrderAmount());

		return paymentRepository.save(new Payment(paymentRequest.getOrderId(), paymentRequest.getStudentRegistration(),
				paymentRequest.getTotalOrderAmount(), paymentStatus));
	}

	@Override
	public List<Payment> getStudentCreditCardPaymentStatement(LocalDateTime startDate, LocalDateTime endDate,
															  String studentRegistration) {
		return paymentRepository.findByStudentIdAndUpdateDateBetween(studentRegistration, startDate, endDate);
	}

	private PaymentStatus authorizePaymentForTransaction(String cardNumber, Integer verificationCode,
														 BigDecimal totalOrderAmount){

		if (!creditCardValidateService.validateCreditCardInIssuer(cardNumber, verificationCode))
			return PaymentStatus.ABORTED;

		if(!creditCardValidateService.validateCreditCardTransaction(cardNumber, verificationCode, totalOrderAmount))
			return PaymentStatus.REJECTED;

		if (!creditCardValidateService.isNeededValidateFraudAnalysis(cardNumber))
			return PaymentStatus.PENDING;

		return PaymentStatus.APPROVED;
	}
}

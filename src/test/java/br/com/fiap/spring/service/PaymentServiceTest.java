package br.com.fiap.spring.service;

import br.com.fiap.spring.advice.exceptions.StudentCreditCardConflictException;
import br.com.fiap.spring.dto.CreditCardRequest;
import br.com.fiap.spring.dto.PaymentRequest;
import br.com.fiap.spring.entity.Payment;
import br.com.fiap.spring.entity.StudentCreditCard;
import br.com.fiap.spring.enums.PaymentStatus;
import br.com.fiap.spring.repository.PaymentRepository;
import br.com.fiap.spring.repository.StudentCreditCardRepository;
import br.com.fiap.spring.service.impl.PaymentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PaymentServiceTest {

	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private CreditCardValidateService creditCardValidateService;

	@Mock
	private StudentCreditCardRepository studentCreditCardRepository;

	@InjectMocks
	private PaymentServiceImpl paymentService;

	@Before
	public void setUp() {
		initMocks(this);
	}

	private static Integer ORDER_ID = 1;
	private static Integer GENERIC_ID = 1;
	private static BigDecimal TOTAL_ORDER_AMOUNT = new BigDecimal(1);
	private static String STUDENT_REGISTRATION = "1234567";
	private static final String CARD_NUMBER = "1234567812345678";
	private static final int VERIFICATION_CODE = 123;
	private static CreditCardRequest CREDIT_CARD = new CreditCardRequest(CARD_NUMBER, VERIFICATION_CODE);
	private static String NAME = "FULL NAME";
	private static String COURSE = "001-01";
	private static String EXPIRATION_DATE = "04/23";

	@Test
	public void shouldProcessAsApprovedAStudentCreditCardPayment(){
		StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
				CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);
		PaymentRequest paymentRequest = new PaymentRequest(ORDER_ID, TOTAL_ORDER_AMOUNT, STUDENT_REGISTRATION, CREDIT_CARD);
		Payment payment = new Payment(GENERIC_ID, paymentRequest.getOrderId(), paymentRequest.getTotalOrderAmount(),
				paymentRequest.getStudentRegistration(), PaymentStatus.APPROVED, LocalDateTime.now(), LocalDateTime.now());

		when(studentCreditCardRepository.findByRegistration(paymentRequest.getStudentRegistration()))
				.thenReturn(studentCreditCard);

		when(creditCardValidateService.validateCreditCardInIssuer(CARD_NUMBER, VERIFICATION_CODE)).thenReturn(Boolean.TRUE);
		when(creditCardValidateService.validateCreditCardTransaction(CARD_NUMBER, VERIFICATION_CODE, TOTAL_ORDER_AMOUNT)).thenReturn(Boolean.TRUE);
		when(creditCardValidateService.isNeededValidateFraudAnalysis(CARD_NUMBER)).thenReturn(Boolean.TRUE);

		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		assertEquals(GENERIC_ID, paymentService.processStudentCreditCardPayment(paymentRequest).getId());
	}

	@Test
	public void shouldProcessAsAbortedAStudentCreditCardPayment(){
		StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
				CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);
		PaymentRequest paymentRequest = new PaymentRequest(ORDER_ID, TOTAL_ORDER_AMOUNT, STUDENT_REGISTRATION, CREDIT_CARD);
		Payment payment = new Payment(GENERIC_ID, paymentRequest.getOrderId(), paymentRequest.getTotalOrderAmount(),
				paymentRequest.getStudentRegistration(), PaymentStatus.ABORTED, LocalDateTime.now(), LocalDateTime.now());

		when(studentCreditCardRepository.findByRegistration(paymentRequest.getStudentRegistration()))
				.thenReturn(studentCreditCard);

		when(creditCardValidateService.validateCreditCardInIssuer(CARD_NUMBER, VERIFICATION_CODE)).thenReturn(Boolean.FALSE);

		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		assertEquals(GENERIC_ID, paymentService.processStudentCreditCardPayment(paymentRequest).getId());
	}

	@Test
	public void shouldProcessAsRejectedAStudentCreditCardPayment(){
		StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
				CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);
		PaymentRequest paymentRequest = new PaymentRequest(ORDER_ID, TOTAL_ORDER_AMOUNT, STUDENT_REGISTRATION, CREDIT_CARD);
		Payment payment = new Payment(GENERIC_ID, paymentRequest.getOrderId(), paymentRequest.getTotalOrderAmount(),
				paymentRequest.getStudentRegistration(), PaymentStatus.REJECTED, LocalDateTime.now(), LocalDateTime.now());

		when(studentCreditCardRepository.findByRegistration(paymentRequest.getStudentRegistration()))
				.thenReturn(studentCreditCard);

		when(creditCardValidateService.validateCreditCardInIssuer(CARD_NUMBER, VERIFICATION_CODE)).thenReturn(Boolean.TRUE);
		when(creditCardValidateService.validateCreditCardTransaction(CARD_NUMBER, VERIFICATION_CODE, TOTAL_ORDER_AMOUNT)).thenReturn(Boolean.FALSE);

		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		assertEquals(GENERIC_ID, paymentService.processStudentCreditCardPayment(paymentRequest).getId());
	}

	@Test
	public void shouldProcessAPendingAStudentCreditCardPayment(){
		StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
				CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);
		PaymentRequest paymentRequest = new PaymentRequest(ORDER_ID, TOTAL_ORDER_AMOUNT, STUDENT_REGISTRATION, CREDIT_CARD);
		Payment payment = new Payment(GENERIC_ID, paymentRequest.getOrderId(), paymentRequest.getTotalOrderAmount(),
				paymentRequest.getStudentRegistration(), PaymentStatus.PENDING, LocalDateTime.now(), LocalDateTime.now());

		when(studentCreditCardRepository.findByRegistration(paymentRequest.getStudentRegistration()))
				.thenReturn(studentCreditCard);

		when(creditCardValidateService.validateCreditCardInIssuer(CARD_NUMBER, VERIFICATION_CODE)).thenReturn(Boolean.TRUE);
		when(creditCardValidateService.validateCreditCardTransaction(CARD_NUMBER, VERIFICATION_CODE, TOTAL_ORDER_AMOUNT)).thenReturn(Boolean.TRUE);
		when(creditCardValidateService.isNeededValidateFraudAnalysis(CARD_NUMBER)).thenReturn(Boolean.FALSE);

		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		assertEquals(GENERIC_ID, paymentService.processStudentCreditCardPayment(paymentRequest).getId());
	}

	@Test(expected = StudentCreditCardConflictException.class)
	public void shouldThrowAStudentCreditCardConflictExceptionWhenProcessAStudentCreditCardPayment(){
		StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
				CARD_NUMBER, EXPIRATION_DATE, 321);
		PaymentRequest paymentRequest = new PaymentRequest(ORDER_ID, TOTAL_ORDER_AMOUNT, STUDENT_REGISTRATION, CREDIT_CARD);

		when(studentCreditCardRepository.findByRegistration(paymentRequest.getStudentRegistration()))
				.thenReturn(studentCreditCard);

		assertEquals(GENERIC_ID, paymentService.processStudentCreditCardPayment(paymentRequest).getId());
	}

	@Test
	public void shouldGetStudentCreditCardPaymentStatement(){
		LocalDateTime startDate = LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now();
		Payment payment = new Payment(GENERIC_ID, ORDER_ID, TOTAL_ORDER_AMOUNT, STUDENT_REGISTRATION,
				PaymentStatus.APPROVED, startDate, endDate);

		when(paymentRepository.findByStudentIdAndUpdateDateBetween(STUDENT_REGISTRATION, startDate, endDate))
				.thenReturn(Collections.singletonList(payment));

		assertEquals(1, paymentService.getStudentCreditCardPaymentStatement(startDate, endDate,
				STUDENT_REGISTRATION).size());
	}
}

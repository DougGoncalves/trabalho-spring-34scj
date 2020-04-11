package br.com.fiap.spring.service;

import br.com.fiap.spring.advice.exceptions.PreRegistrationFailedException;
import br.com.fiap.spring.advice.exceptions.StudentCreditCardNotFoundException;
import br.com.fiap.spring.dto.StudentCreditCardRequest;
import br.com.fiap.spring.entity.StudentCreditCard;
import br.com.fiap.spring.repository.StudentCreditCardRepository;
import br.com.fiap.spring.service.impl.StudentCreditCardServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class StudentCreditCardServiceTest {

	@Mock
	private StudentCreditCardRepository studentCreditCardRepository;

	@Mock
	private Job studentCreditCardJob;

	@Mock
	private JobLauncher jobLauncher;

	@InjectMocks
	private StudentCreditCardServiceImpl studentCreditCardService;

	@Before
	public void setUp() {
		initMocks(this);
	}

	private static Integer GENERIC_ID = 1;
	private static String STUDENT_REGISTRATION = "1234567";
	private static final String CARD_NUMBER = "1234567812345678";
	private static final int VERIFICATION_CODE = 123;
	private static String NAME = "FULL NAME";
	private static String COURSE = "001-01";
	private static String EXPIRATION_DATE = "04/23";

	@Test
	public void shouldGetAllStudentsPaged(){
		StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
				CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);
		Page<StudentCreditCard> studentCreditCardList = new PageImpl<>(
				new ArrayList<>(Collections.singletonList(studentCreditCard)));

		int numberOfPage = 1;
		int sizePerPage = 20;
		Pageable pageable = PageRequest.of(numberOfPage, sizePerPage);

		when(studentCreditCardRepository.count()).thenReturn(1L);
		when(studentCreditCardRepository.findAll(pageable)).thenReturn(studentCreditCardList);

		Page<StudentCreditCard> studentCreditCardListResponse = studentCreditCardService.getAllStudentsCreditCard(pageable);

		assertEquals(1, studentCreditCardListResponse.getTotalPages());
		assertEquals(1, studentCreditCardListResponse.getContent().size());
		assertEquals(GENERIC_ID, studentCreditCardListResponse.getContent().stream().findFirst().get().getId());
	}

	@Test
	public void shouldGetStudentsCreditCardById(){
		StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
				CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

		when(studentCreditCardRepository.findById(GENERIC_ID)).thenReturn(Optional.of(studentCreditCard));

		assertEquals(GENERIC_ID, studentCreditCardService.getStudentCreditCardById(GENERIC_ID).getId());
	}

	@Test(expected = StudentCreditCardNotFoundException.class)
	public void shouldThrowAStudentNotFoundExceptionWhenGetStudentsCreditCardById(){
		when(studentCreditCardRepository.findById(GENERIC_ID)).thenReturn(Optional.empty());

		studentCreditCardService.getStudentCreditCardById(GENERIC_ID);
	}

	@Test
	public void shouldCreateStudentsCreditCard(){
		StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
				CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

		StudentCreditCardRequest studentCreditCardRequest = new StudentCreditCardRequest(STUDENT_REGISTRATION, NAME,
				COURSE, CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

		when(studentCreditCardRepository.save(any(StudentCreditCard.class))).thenReturn(studentCreditCard);

		assertEquals(GENERIC_ID, studentCreditCardService.createStudentCreditCard(studentCreditCardRequest).getId());
	}

	@Test
	public void shouldUpdateStudentCreditCard(){
		StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
				CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

		StudentCreditCardRequest studentCreditCardRequest = new StudentCreditCardRequest(STUDENT_REGISTRATION, NAME,
				COURSE, CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

		when(studentCreditCardRepository.findById(GENERIC_ID)).thenReturn(Optional.of(studentCreditCard));

		studentCreditCardService.updateStudentCreditCard(GENERIC_ID, studentCreditCardRequest);

		verify(studentCreditCardRepository, times(1)).save(any());
	}

	@Test(expected = StudentCreditCardNotFoundException.class)
	public void shouldThrowStudentCreditCardNotFoundExceptionWhenUpdateStudentCreditCard(){
		StudentCreditCardRequest studentCreditCardRequest = new StudentCreditCardRequest(STUDENT_REGISTRATION, NAME,
				COURSE, CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

		when(studentCreditCardRepository.findById(GENERIC_ID)).thenReturn(Optional.empty());

		studentCreditCardService.updateStudentCreditCard(GENERIC_ID, studentCreditCardRequest);

		verify(studentCreditCardRepository, times(0)).save(any());
	}

	@Test
	public void shouldDeleteStudentCreditCard(){
		StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
				CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

		when(studentCreditCardRepository.findById(GENERIC_ID)).thenReturn(Optional.of(studentCreditCard));

		studentCreditCardService.deleteStudentCreditCard(GENERIC_ID);

		verify(studentCreditCardRepository, times(1)).delete(any());
	}

	@Test(expected = StudentCreditCardNotFoundException.class)
	public void shouldThrowStudentCreditCardNotFoundExceptionWhenDeleteStudentCreditCard(){
		when(studentCreditCardRepository.findById(GENERIC_ID)).thenReturn(Optional.empty());

		studentCreditCardService.deleteStudentCreditCard(GENERIC_ID);

		verify(studentCreditCardRepository, times(0)).delete(any());
	}

	@Test
	public void shouldProcessPreRegistration() throws Exception {
		JobExecution jobExecution = mock(JobExecution.class);

		when(jobLauncher.run(eq(studentCreditCardJob), any(JobParameters.class))).thenReturn(jobExecution);

		studentCreditCardService.processPreRegistration();
	}

	@Test(expected = PreRegistrationFailedException.class)
	public void shouldThrowPreRegistrationFailedExceptionWhenProcessPreRegistration() throws Exception {

		when(jobLauncher.run(eq(studentCreditCardJob), any(JobParameters.class)))
				.thenThrow(new JobParametersInvalidException("Error"));

		studentCreditCardService.processPreRegistration();
	}
}

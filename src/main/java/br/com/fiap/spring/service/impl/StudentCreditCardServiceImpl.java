package br.com.fiap.spring.service.impl;

import br.com.fiap.spring.advice.exceptions.PreRegistrationFailedException;
import br.com.fiap.spring.advice.exceptions.StudentCreditCardNotFoundException;
import br.com.fiap.spring.dto.StudentCreditCardRequest;
import br.com.fiap.spring.entity.StudentCreditCard;
import br.com.fiap.spring.repository.StudentCreditCardRepository;
import br.com.fiap.spring.service.StudentCreditCardService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class StudentCreditCardServiceImpl implements StudentCreditCardService {

	@Autowired
	private StudentCreditCardRepository studentCreditCardRepository;

	@Autowired
	@Qualifier("studentCreditCardJob")
	private Job studentCreditCardJob;

	@Autowired
	@Qualifier("jobLauncher")
	private JobLauncher jobLauncher;

	@Override
	public Page<StudentCreditCard> getAllStudentsCreditCard(Pageable pageable) {

		long count = studentCreditCardRepository.count();
		Page<StudentCreditCard> students = studentCreditCardRepository.findAll(pageable);

		return new PageImpl<>(students.stream().collect(Collectors.toList()),
				students.getPageable(), count);
	}

	@Override
	public StudentCreditCard getStudentCreditCardById(Integer id) {
		return studentCreditCardRepository.findById(id).orElseThrow(() ->
				new StudentCreditCardNotFoundException("Estudante não encontrado"));
	}

	@Override
	public StudentCreditCard createStudentCreditCard(StudentCreditCardRequest studentCreditCardRequest) {
		return studentCreditCardRepository.save(new StudentCreditCard(studentCreditCardRequest.getRegistration(), studentCreditCardRequest.getName(),
				studentCreditCardRequest.getCourse(), studentCreditCardRequest.getCardNumber(), studentCreditCardRequest.getExpirationDate(),
				studentCreditCardRequest.getVerificationCode()));
	}

	@Override
	public void updateStudentCreditCard(Integer id, StudentCreditCardRequest studentCreditCardRequest) {
		updateStudentsCreditCard(getStudent(id, studentCreditCardRequest));
	}

	private void updateStudentsCreditCard(StudentCreditCard student) {
		StudentCreditCard storedStudent = studentCreditCardRepository.findById(student.getId())
				.orElseThrow(() -> new StudentCreditCardNotFoundException("Estudante não encontrado"));

		storedStudent.setName(student.getName());
		storedStudent.setRegistration(student.getRegistration());
		storedStudent.setCourse(student.getCourse());
		storedStudent.setCardNumber(student.getCardNumber());
		storedStudent.setExpirationDate(student.getExpirationDate());
		storedStudent.setVerificationCode(student.getVerificationCode());

		studentCreditCardRepository.save(storedStudent);
	}

	@Override
	public void deleteStudentCreditCard(Integer id) {
		studentCreditCardRepository.delete(studentCreditCardRepository.findById(id).orElseThrow(() ->
				new StudentCreditCardNotFoundException("Estudante não encontrado")));
	}

	@Override
	public void processPreRegistration(){
		try {
			JobExecution execution = jobLauncher.run(studentCreditCardJob, new JobParameters());
			System.out.println("Job Status : " + execution.getStatus());
			System.out.println("Job completed");
		} catch (Exception e) {
			System.out.println("Job failed");
			throw new PreRegistrationFailedException("Ocorreu um erro durante o processamento do arquivo de carga.");
		}
	}

	private StudentCreditCard getStudent(Integer id, StudentCreditCardRequest studentCreditCardRequest) {
		return new StudentCreditCard(id, studentCreditCardRequest.getRegistration(), studentCreditCardRequest.getName(),
				studentCreditCardRequest.getCourse(), studentCreditCardRequest.getCardNumber(), studentCreditCardRequest.getExpirationDate(),
				studentCreditCardRequest.getVerificationCode());
	}
}
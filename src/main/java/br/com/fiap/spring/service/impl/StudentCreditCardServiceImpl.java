package br.com.fiap.spring.service.impl;

import br.com.fiap.spring.advice.ResponseError;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class StudentCreditCardServiceImpl implements StudentCreditCardService {

	@Autowired
	private StudentCreditCardRepository studentRepository;

	@Autowired
	@Qualifier("studentCreditCardJob")
	private Job studentCreditCardJob;

	@Autowired
	@Qualifier("jobLauncher")
	private JobLauncher jobLauncher;

	@Override
	public Page<StudentCreditCard> getAllStudents(Pageable pageable) {

		long count = studentRepository.count();
		Page<StudentCreditCard> students = studentRepository.findAll(pageable);

		return new PageImpl<>(students.stream().collect(Collectors.toList()),
				students.getPageable(), count);
	}

	@Override
	public StudentCreditCard getStudentById(Integer id) {
		return studentRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Estudante não encontrado"));
	}

	@Override
	public StudentCreditCard createStudent(StudentCreditCardRequest studentCreditCardRequest) {
		return studentRepository.save(new StudentCreditCard(studentCreditCardRequest.getRegistration(), studentCreditCardRequest.getName(),
				studentCreditCardRequest.getCourse(), studentCreditCardRequest.getCardNumber(), studentCreditCardRequest.getExpirationDate(),
				studentCreditCardRequest.getVerificationCode()));
	}

	@Override
	public void updateStudent(Integer id, StudentCreditCardRequest studentCreditCardRequest) {
		updateStudent(getStudent(id, studentCreditCardRequest));
	}

	private void updateStudent(StudentCreditCard student) {
		StudentCreditCard storedStudent = studentRepository.findById(student.getId())
				.orElseThrow(() -> new ResponseError(HttpStatus.NOT_FOUND, "Estudante não encontrado"));

		storedStudent.setName(student.getName());
		storedStudent.setRegistration(student.getRegistration());
		storedStudent.setCourse(student.getCourse());
		storedStudent.setCardNumber(student.getCardNumber());
		storedStudent.setExpirationDate(student.getExpirationDate());
		storedStudent.setVerificationCode(student.getVerificationCode());

		studentRepository.save(storedStudent);
	}

	@Override
	public void deleteStudent(Integer id) {
		studentRepository.delete(studentRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Estudante não encontrado")));
	}

	@Override
	public void processPreRegistration(){
		try {
			JobExecution execution = jobLauncher.run(studentCreditCardJob, new JobParameters());
			System.out.println("Job Status : " + execution.getStatus());
			System.out.println("Job completed");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Job failed");
		}
	}

	private StudentCreditCard getStudent(Integer id, StudentCreditCardRequest studentCreditCardRequest) {
		return new StudentCreditCard(id, studentCreditCardRequest.getRegistration(), studentCreditCardRequest.getName(),
				studentCreditCardRequest.getCourse(), studentCreditCardRequest.getCardNumber(), studentCreditCardRequest.getExpirationDate(),
				studentCreditCardRequest.getVerificationCode());
	}

}

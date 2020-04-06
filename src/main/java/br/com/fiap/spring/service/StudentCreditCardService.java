package br.com.fiap.spring.service;

import br.com.fiap.spring.dto.StudentCreditCardRequest;
import br.com.fiap.spring.entity.StudentCreditCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentCreditCardService {
	Page<StudentCreditCard> getAllStudents(Pageable pageable);
	StudentCreditCard getStudentById(Integer id);
	StudentCreditCard createStudent(StudentCreditCardRequest studentCreditCardRequest);
	void updateStudent(Integer id, StudentCreditCardRequest studentCreditCardRequest);
	void deleteStudent(Integer id);
	void processPreRegistration();
}

package br.com.fiap.spring.service;

import br.com.fiap.spring.dto.StudentCreditCardRequest;
import br.com.fiap.spring.entity.StudentCreditCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentCreditCardService {
	Page<StudentCreditCard> getAllStudentsCreditCard(Pageable pageable);
	StudentCreditCard getStudentCreditCardById(Integer id);
	StudentCreditCard createStudentCreditCard(StudentCreditCardRequest studentCreditCardRequest);
	void updateStudentCreditCard(Integer id, StudentCreditCardRequest studentCreditCardRequest);
	void deleteStudentCreditCard(Integer id);
	void processPreRegistration();
}

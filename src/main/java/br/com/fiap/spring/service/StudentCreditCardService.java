package br.com.fiap.spring.service;

import br.com.fiap.spring.dto.StudentCreditCardRequest;
import br.com.fiap.spring.entity.StudentCreditCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentCreditCardService {
	Page<StudentCreditCard> getAllStudentsCreditCard(Pageable pageable);
	StudentCreditCard getStudentsCreditCardById(Integer id);
	StudentCreditCard createStudentsCreditCard(StudentCreditCardRequest studentCreditCardRequest);
	void updateStudentsCreditCard(Integer id, StudentCreditCardRequest studentCreditCardRequest);
	void deleteStudentsCreditCard(Integer id);
	void processPreRegistration();
}

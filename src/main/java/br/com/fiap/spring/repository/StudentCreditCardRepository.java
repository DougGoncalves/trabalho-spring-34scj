package br.com.fiap.spring.repository;

import br.com.fiap.spring.entity.StudentCreditCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCreditCardRepository extends JpaRepository<StudentCreditCard, Integer> {
    Page<StudentCreditCard> findAll(Pageable pageable);
    StudentCreditCard findByRegistration(String registration);
}

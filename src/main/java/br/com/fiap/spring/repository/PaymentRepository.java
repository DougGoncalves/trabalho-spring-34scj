package br.com.fiap.spring.repository;

import br.com.fiap.spring.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByStudentIdAndUpdateDateBetween(String studentId, LocalDateTime startLocalDateTime, LocalDateTime endDate);
}

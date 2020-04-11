package br.com.fiap.spring.advice.exceptions;

public class StudentCreditCardConflictException extends RuntimeException {
    public StudentCreditCardConflictException(String message) {
        super(message);
    }
}
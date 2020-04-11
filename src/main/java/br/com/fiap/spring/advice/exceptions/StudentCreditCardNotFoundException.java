package br.com.fiap.spring.advice.exceptions;

public class StudentCreditCardNotFoundException extends RuntimeException {
    public StudentCreditCardNotFoundException(String message) {
        super(message);
    }
}
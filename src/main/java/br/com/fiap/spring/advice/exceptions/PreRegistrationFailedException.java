package br.com.fiap.spring.advice.exceptions;

public class PreRegistrationFailedException extends RuntimeException {
    public PreRegistrationFailedException(String message) {
        super(message);
    }
}
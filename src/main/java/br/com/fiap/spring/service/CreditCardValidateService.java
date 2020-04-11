package br.com.fiap.spring.service;

import java.math.BigDecimal;

public interface CreditCardValidateService {
    Boolean validateCreditCardInIssuer(String cardNumber, Integer verificationCode);
    Boolean validateCreditCardTransaction(String cardNumber, Integer verificationCode, BigDecimal totalOrderAmount);
    Boolean isNeededValidateFraudAnalysis(String cardNumber);
}

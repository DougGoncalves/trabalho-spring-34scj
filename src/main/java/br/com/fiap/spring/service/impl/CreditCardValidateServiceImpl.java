package br.com.fiap.spring.service.impl;

import br.com.fiap.spring.service.CreditCardValidateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class CreditCardValidateServiceImpl implements CreditCardValidateService {

	@Override
	public Boolean validateCreditCardInIssuer(String cardNumber, Integer verificationCode){
		return new Random().nextBoolean();
	}

	@Override
	public Boolean validateCreditCardTransaction(String cardNumber, Integer verificationCode,
												 BigDecimal totalOrderAmount){
		return new Random().nextBoolean();
	}

	@Override
	public Boolean isNeededValidateFraudAnalysis(String cardNumber){
		return new Random().nextBoolean();
	}
}

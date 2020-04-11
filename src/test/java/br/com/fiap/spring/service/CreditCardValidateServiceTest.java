package br.com.fiap.spring.service;

import br.com.fiap.spring.service.impl.CreditCardValidateServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardValidateServiceTest {

    private static final String CARD_NUMBER = "1234567812345678";
    private static final int VERIFICATION_CODE = 123;

    @InjectMocks
    private CreditCardValidateServiceImpl creditCardValidateService;

    @Test
    public void shouldValidateCreditCardInIssuer(){
        Assert.assertNotNull(creditCardValidateService.validateCreditCardInIssuer(CARD_NUMBER, VERIFICATION_CODE));
    }

    @Test
    public void shouldValidateCreditCardTransaction(){
        Assert.assertNotNull(creditCardValidateService.validateCreditCardTransaction(
                CARD_NUMBER, VERIFICATION_CODE, new BigDecimal(1)));
    }

    @Test
    public void shouldValidateIfIsNeededValidateFraudAnalysis(){
        Assert.assertNotNull(creditCardValidateService.isNeededValidateFraudAnalysis(CARD_NUMBER));
    }

}

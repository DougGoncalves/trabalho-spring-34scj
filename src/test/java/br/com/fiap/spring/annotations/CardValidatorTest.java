package br.com.fiap.spring.annotations;

import org.junit.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CardValidatorTest {

    @Test
    public void shouldValidateACreditCardWithSixteenDigits(){
        assertEquals(Boolean.TRUE, new CardValidator().isValid("4539711103420778",
                mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void shouldValidateACreditCardWithFifteenDigits(){
        assertEquals(Boolean.TRUE, new CardValidator().isValid("348772438022464",
                mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void shouldReturnFalseWhenValidateACreditCardWithInvalidFifteenDigits(){
        assertEquals(Boolean.FALSE, new CardValidator().isValid("123451234512345",
                mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void shouldReturnFalseWhenValidateANullCreditCard(){
        assertEquals(Boolean.FALSE, new CardValidator().isValid(null, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void shouldReturnTrueWhenValidateANullCreditCardThatIsNotRequired(){
        CardValidator cardValidator = new CardValidator();
        CardValidator.CardValidation cardValidation = mock(CardValidator.CardValidation.class);
        when(cardValidation.required()).thenReturn(Boolean.FALSE);

        cardValidator.initialize(cardValidation);

        assertEquals(Boolean.TRUE, cardValidator.isValid(null, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void shouldValidateACreditCardWithSixteenDigitsThatIsNotRequired(){
        CardValidator cardValidator = new CardValidator();
        CardValidator.CardValidation cardValidation = mock(CardValidator.CardValidation.class);
        when(cardValidation.required()).thenReturn(Boolean.FALSE);

        cardValidator.initialize(cardValidation);

        assertEquals(Boolean.TRUE, cardValidator.isValid("4539711103420778", mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void shouldReturnFalseWhenValidateACreditCardInvalid(){
        assertEquals(Boolean.FALSE, new CardValidator().isValid("INVALID_CARD_VAL", mock(ConstraintValidatorContext.class)));
    }
}

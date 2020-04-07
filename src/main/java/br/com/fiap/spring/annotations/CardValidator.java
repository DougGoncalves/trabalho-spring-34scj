package br.com.fiap.spring.annotations;

import io.micrometer.core.instrument.util.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class CardValidator implements ConstraintValidator<CardValidator.CardValidation, String> {

    public static final int CREDIT_CARD_WITH_FIFTEENTH_CHECK_DIGIT = 15;
    public static final int CREDIT_CARD_WITH_SIXTEENTH_CHECK_DIGIT = 16;

    @Documented
    @Constraint(validatedBy = CardValidator.class)
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER,
        ElementType.CONSTRUCTOR})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CardValidation {

        String message() default "O número do cartão informado é inválido.";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        boolean required() default true;

    }

    private boolean required = true;

    @Override
    public void initialize(CardValidation value) {
        required = value.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (required) {
            if (StringUtils.isNotEmpty(value)) {
                return validate(value);
            }
            return false;
        }

        if (StringUtils.isNotEmpty(value) || StringUtils.isNotBlank(value)) {
            return validate(value);
        }
        return true;
    }

    private Boolean validate(String value){
        String digitNumber;
        int sum = 0;

        try {
            if (value.length() <= CREDIT_CARD_WITH_FIFTEENTH_CHECK_DIGIT){
                for(int i = 0; i < value.length(); i++) {
                    digitNumber = (value.substring(i,i+1));
    
                    if (i % 2 == 0){
                        sum += (Integer.parseInt(digitNumber));
                    } else {
                        if ((Integer.parseInt(digitNumber) * 2) > 9) {
                            sum += ((Integer.parseInt(digitNumber) * 2) - 9);
                        } else {
                            sum += ((Integer.parseInt(digitNumber) * 2));
                        }
                    }
                }
            }
    
            if (value.length() >= CREDIT_CARD_WITH_SIXTEENTH_CHECK_DIGIT){
                for(int i = 0; i < value.length(); i++){
                    digitNumber = (value.substring(i,i+1));
                    if (i % 2 == 0){
                        if ((Integer.parseInt(digitNumber) * 2) > 9){
                            sum += ((Integer.parseInt(digitNumber) * 2) - 9);
                        } else {
                            sum += ((Integer.parseInt(digitNumber) * 2));
                        }
                    } else {
                        sum +=(Integer.parseInt(digitNumber));
                    }
                }
            }
    
            if (sum % 10 == 0) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (NumberFormatException ex){
            return Boolean.FALSE;
        }
    }
}

package br.com.fiap.spring.dto;

import static br.com.fiap.spring.annotations.CardValidator.CardValidation;
import javax.validation.constraints.NotNull;

public class CreditCardRequest {

    @CardValidation
    @NotNull
    private String cardNumber;

    @NotNull
    private Integer verificationCode;

    public CreditCardRequest() {
    }

    public CreditCardRequest(String cardNumber, Integer verificationCode) {
        this.cardNumber = cardNumber;
        this.verificationCode = verificationCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }
}

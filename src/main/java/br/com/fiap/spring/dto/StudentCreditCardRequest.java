package br.com.fiap.spring.dto;

import static br.com.fiap.spring.annotations.CardValidator.CardValidation;

import javax.validation.constraints.NotNull;

public class StudentCreditCardRequest {

    @NotNull
    private String registration;

    @NotNull
    private String name;

    @NotNull
    private String course;

    @CardValidation
    @NotNull
    private String cardNumber;

    @NotNull
    private String expirationDate;

    @NotNull
    private Integer verificationCode;

    public StudentCreditCardRequest() {
    }

    public StudentCreditCardRequest(String registration, String name, String course, String cardNumber,
                                    String expirationDate, Integer verificationCode) {
        this.registration = registration;
        this.name = name;
        this.course = course;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.verificationCode = verificationCode;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(Integer verificationCode) {
        this.verificationCode = verificationCode;
    }
}


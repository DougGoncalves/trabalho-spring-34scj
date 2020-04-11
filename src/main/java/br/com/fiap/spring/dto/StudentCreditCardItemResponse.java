package br.com.fiap.spring.dto;

public class StudentCreditCardItemResponse {

    private Integer id;
    private String registration;
    private String name;
    private String course;
    private String cardNumber;
    private String expirationDate;
    private Integer verificationCode;

    public StudentCreditCardItemResponse(Integer id, String registration, String name, String course, String cardNumber,
                                         String expirationDate, Integer verificationCode) {
        this.id = id;
        this.registration = registration;
        this.name = name;
        this.course = course;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.verificationCode = verificationCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

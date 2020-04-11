package br.com.fiap.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "STUDENT_CREDIT_CARD")
public class StudentCreditCard {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "generator", allocationSize = 1, sequenceName = "student_credit_card_sequence")
    @Column
    private Integer id;
    @Column(name = "REGISTRATION_CODE")
    private String registration;
    @Column(name = "NAME")
    private String name;
    @Column(name = "COURSE")
    private String course;
    @Column(name = "CARD_NUMBER")
    private String cardNumber;
    @Column(name = "EXPIRATION_DATE")
    private String expirationDate;
    @Column(name = "VERIFICATION_CODE")
    private Integer verificationCode;

    public StudentCreditCard() {
    }

    public StudentCreditCard(String registration, String name, String course, String cardNumber,
                   String expirationDate, Integer verificationCode) {
        this.registration = registration;
        this.name = name;
        this.course = course;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.verificationCode = verificationCode;
    }

    public StudentCreditCard(Integer id, String registration, String name, String course, String cardNumber,
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

    public String getCourse() { return course; }

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
package br.com.fiap.spring.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentRequest {

    @NotNull
    private Integer orderId;

    @NotNull
    private BigDecimal totalOrderAmount;

    @NotNull
    private String studentRegistration;

    @Valid
    @NotNull
    private CreditCardRequest creditCard;

    public PaymentRequest() {
    }

    public PaymentRequest(Integer orderId, BigDecimal totalOrderAmount, String studentRegistration,
                          CreditCardRequest creditCard) {
        this.orderId = orderId;
        this.totalOrderAmount = totalOrderAmount;
        this.studentRegistration = studentRegistration;
        this.creditCard = creditCard;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public String getStudentRegistration() {
        return studentRegistration;
    }

    public void setStudentRegistration(String studentRegistrationId) {
        this.studentRegistration = studentRegistrationId;
    }

    public CreditCardRequest getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardRequest creditCard) {
        this.creditCard = creditCard;
    }
}

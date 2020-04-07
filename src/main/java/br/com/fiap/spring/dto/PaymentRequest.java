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
    private String studentId;

    @Valid
    @NotNull
    private CreditCardRequest creditCard;

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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public CreditCardRequest getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardRequest creditCard) {
        this.creditCard = creditCard;
    }
}

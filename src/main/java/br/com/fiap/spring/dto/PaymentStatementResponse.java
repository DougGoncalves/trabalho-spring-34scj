package br.com.fiap.spring.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class PaymentStatementResponse {

    private Integer id;
    private Integer orderId;
    private BigDecimal totalOrderAmount;
    private String status;
    private String creationDate;
    private String updateDate;

    public PaymentStatementResponse() {
    }

    public PaymentStatementResponse(Integer id, Integer orderId, BigDecimal totalOrderAmount, String status,
                                    String creationDate, String updateDate) {
        this.id = id;
        this.orderId = orderId;
        this.totalOrderAmount = totalOrderAmount;
        this.status = status;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}

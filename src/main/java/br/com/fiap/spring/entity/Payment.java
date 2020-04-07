package br.com.fiap.spring.entity;

import br.com.fiap.spring.enums.PaymentStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;

@Entity(name = "PAYMENT")
public class Payment extends BaseAudit {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(initialValue = 1, name = "generator", sequenceName = "payment_sequence")
    @Column
    private Integer id;

    @Column(name = "ORDER_ID")
    private Integer orderId;

    @Column(name = "TOTAL_ORDER_AMOUNT")
    private BigDecimal totalOrderAmount;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "STATUS")
    private PaymentStatus status;

    public Payment() {
    }

    public Payment(Integer orderId, String studentId,  BigDecimal totalOrderAmount, PaymentStatus status) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.totalOrderAmount = totalOrderAmount;
        this.status = status;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setSituation(PaymentStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }
}
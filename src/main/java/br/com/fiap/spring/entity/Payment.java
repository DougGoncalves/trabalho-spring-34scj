package br.com.fiap.spring.entity;

import br.com.fiap.spring.enums.PaymentStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "PAYMENT")
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(generator = "generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(initialValue = 11, allocationSize = 1, name = "generator", sequenceName = "payment_sequence")
    @Column(name = "PAYMENT_ID")
    private Integer id;

    @Column(name = "ORDER_ID")
    private Integer orderId;

    @Column(name = "TOTAL_ORDER_AMOUNT")
    private BigDecimal totalOrderAmount;

    @Column(name = "STUDENT_ID")
    private String studentId;

    @Column(name = "STATUS")
    private PaymentStatus status;

    @Column(name = "CREATION_DATE", nullable = false)
    @CreatedDate
    private LocalDateTime creationDate;

    @Column(name = "UPDATE_DATE")
    @LastModifiedDate
    private LocalDateTime updateDate;

    public Payment() {
    }

    public Payment(Integer orderId, String studentId,  BigDecimal totalOrderAmount, PaymentStatus status) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.totalOrderAmount = totalOrderAmount;
        this.status = status;
    }

    public Payment(Integer id, Integer orderId, BigDecimal totalOrderAmount, String studentId, PaymentStatus status,
                   LocalDateTime creationDate, LocalDateTime updateDate) {
        this.id = id;
        this.orderId = orderId;
        this.totalOrderAmount = totalOrderAmount;
        this.studentId = studentId;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
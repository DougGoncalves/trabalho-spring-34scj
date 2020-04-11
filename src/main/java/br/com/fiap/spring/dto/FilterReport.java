package br.com.fiap.spring.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class FilterReport implements Serializable {

    private static final long serialVersionUID = 3886925297648881663L;

    @NotNull
    private Date endDate;

    @NotNull
    private Date startDate;

    @NotNull
    private String studentRegistration;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStudentRegistration() {
        return studentRegistration;
    }

    public void setStudentRegistration(String studentRegistration) {
        this.studentRegistration = studentRegistration;
    }
}

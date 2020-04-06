package br.com.fiap.spring.dto;

import java.util.List;

public class StudentCreditCardResponse {

    private Boolean hasNext;
    private Integer pageNumber;
    private Integer totalPages;
    private List<StudentCreditCardItemResponse> students;

    public StudentCreditCardResponse(Boolean hasNext, Integer pageNumber, Integer totalPages,
                                     List<StudentCreditCardItemResponse> students) {
        this.hasNext = hasNext;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.students = students;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<StudentCreditCardItemResponse> getStudents() {
        return students;
    }

    public void setStudents(List<StudentCreditCardItemResponse> students) {
        this.students = students;
    }
}

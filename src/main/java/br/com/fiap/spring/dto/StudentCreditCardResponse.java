package br.com.fiap.spring.dto;

import java.util.List;

public class StudentCreditCardResponse {

    private Boolean hasNext;
    private Integer pageNumber;
    private Integer totalPages;
    private List<StudentCreditCardItemResponse> studentsCreditCard;

    public StudentCreditCardResponse(Boolean hasNext, Integer pageNumber, Integer totalPages,
                                     List<StudentCreditCardItemResponse> studentsCreditCard) {
        this.hasNext = hasNext;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.studentsCreditCard = studentsCreditCard;
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

    public List<StudentCreditCardItemResponse> getStudentsCreditCard() {
        return studentsCreditCard;
    }

    public void setStudentsCreditCard(List<StudentCreditCardItemResponse> studentsCreditCard) {
        this.studentsCreditCard = studentsCreditCard;
    }
}

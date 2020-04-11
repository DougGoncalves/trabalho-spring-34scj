package br.com.fiap.spring.controller;

import br.com.fiap.spring.dto.StudentCreditCardItemResponse;
import br.com.fiap.spring.dto.StudentCreditCardRequest;
import br.com.fiap.spring.dto.StudentCreditCardResponse;
import br.com.fiap.spring.entity.StudentCreditCard;
import br.com.fiap.spring.service.StudentCreditCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/spring/v1/student/credit-card")
@Api(value = "Gerenciamento de Cartão dos Estudantes")
public class StudentCardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCardController.class);

    private StudentCreditCardService studentCreditCardService;

    private static final int HTTP_STATUS_OK = 200;
    private static final int HTTP_STATUS_CREATED = 201;
    private static final int HTTP_STATUS_NO_CONTENT = 204;
    private static final int PAGE_SIZE = 10;

    @Autowired
    public StudentCardController(final StudentCreditCardService studentCreditCardService){
        this.studentCreditCardService = studentCreditCardService;
    };

    @ApiOperation(value = "Consultar todos os estudantes e os dados de seu cartão")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_STATUS_OK, message = "Lista de estudantes com os respectivos dados de seu cartão",
                    response = StudentCreditCardResponse[].class)
    })
    @GetMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<StudentCreditCardResponse> getAllStudentsCreditCard(@RequestParam("page") int page,
                                                                              @RequestParam("size") int size){
        LOGGER.info("Getting students ... ");

        List<StudentCreditCardItemResponse> studentCreditCardItemResponse = new ArrayList<>();
        Page<StudentCreditCard> students = studentCreditCardService.getAllStudentsCreditCard(handleRequest(page, size));

        students.getContent().forEach(student -> studentCreditCardItemResponse.add(toStudentResponse(student)));

        return ResponseEntity.ok(new StudentCreditCardResponse(students.hasNext(),
                getPageNumber(studentCreditCardItemResponse, students), students.getTotalPages(), studentCreditCardItemResponse));
    }

    @ApiOperation(value = "Consultar um estudante específico com os dados do seu cartão")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_STATUS_OK, message = "Estudante e os respectivos dados de seu cartão",
                    response = StudentCreditCardResponse.class)
    })
    @GetMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<StudentCreditCardItemResponse> getStudentCreditCardById(@PathVariable("id") Integer id){
        LOGGER.info("Getting a specific student ... ");
        return ResponseEntity.ok(toStudentResponse(studentCreditCardService.getStudentCreditCardById(id)));
    }

    @ApiOperation(value = "Excluir um estudante e os e dados do seu cartão")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_STATUS_NO_CONTENT, message = "Dados do cartão do estudante excluído")
    })
    @DeleteMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteStudentCreditCard(@PathVariable("id") Integer id){
        LOGGER.info("Deleting a student credit-card information ... ");
        studentCreditCardService.deleteStudentCreditCard(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Alterar informações de um estudante ou dos dados de seu cartão")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_STATUS_NO_CONTENT, message = "Dados do cartão do estudante atualizados")
    })
    @PutMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateStudentCreditCard(@PathVariable("id") Integer id,
                                                     @Valid @RequestBody StudentCreditCardRequest studentCreditCardRequest){
        LOGGER.info("Updating a specific student ... ");
        studentCreditCardService.updateStudentCreditCard(id, studentCreditCardRequest);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Associar um estudante a dados de seu cartão")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_STATUS_CREATED, message = "Associação realizada")
    })
    @PostMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<StudentCreditCardItemResponse> createStudentCreditCard(@Valid @RequestBody StudentCreditCardRequest studentCreditCardRequest){
        LOGGER.info("Creating the association between student and your credit card ... ");

        StudentCreditCard student = studentCreditCardService.createStudentCreditCard(studentCreditCardRequest);

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/spring/v1/student/credit-card", student.getId())))
                .body(toStudentResponse(student));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Processar o pré-cadastro")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_STATUS_OK, message = "Pré-cadastro processado")
    })
    @PostMapping(value = "/pre-registration", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<Object> processPreRegistation(){
        studentCreditCardService.processPreRegistration();

        return ResponseEntity.ok().build();
    }

    private PageRequest handleRequest(Integer page, Integer size) {
        return PageRequest.of(page != 0 ? page -1 : 0, size != 0 ? size : PAGE_SIZE);
    }

    private int getPageNumber(List<StudentCreditCardItemResponse> studentCreditCardItemResponse, Page<StudentCreditCard> students) {
        return studentCreditCardItemResponse.size() > 0 ? students.getNumber() + 1 : 0;
    }

    private StudentCreditCardItemResponse toStudentResponse(StudentCreditCard student) {
        return new StudentCreditCardItemResponse(student.getId(), student.getRegistration(), student.getName(), student.getCourse(),
                student.getCardNumber(), student.getExpirationDate(), student.getVerificationCode());
    }
}
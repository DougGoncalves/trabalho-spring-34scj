package br.com.fiap.spring.advice;

import br.com.fiap.spring.advice.exceptions.PreRegistrationFailedException;
import br.com.fiap.spring.advice.exceptions.StudentCreditCardConflictException;
import br.com.fiap.spring.advice.exceptions.StudentCreditCardNotFoundException;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.security.access.AccessDeniedException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({
            Exception.class,
            PreRegistrationFailedException.class
    })
    public final ResponseEntity<?> handleGenericExceptions(Exception ex) {
        ResponseError responseError = new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro durante o processamento da requisição.");

        return ResponseEntity
                .status(responseError.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(responseError));
    }

    @ExceptionHandler({
            AccessDeniedException.class
    })
    public final ResponseEntity<?> handleAccessDeniedExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(ex));
    }

    @ExceptionHandler({
            StudentCreditCardConflictException.class
    })
    public final ResponseEntity<?> handleConflictExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(ex));
    }

    @ExceptionHandler({
            StudentCreditCardNotFoundException.class
    })
    public final ResponseEntity<?> handlePreConditionFailedExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(ex));
    }

    @ExceptionHandler({
            ResponseError.class
    })
    public final ResponseEntity<?> handleUnprocessableEntityExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponseError responseError = new ResponseError(
                HttpStatus.BAD_REQUEST,
                "Dados inválidos na requisição. Por favor verifique os dados informados e tente novamente.");

        return ResponseEntity
                .status(responseError.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(responseError));
    }

    private String getBody(Exception ex) {
        JsonObject json = new JsonObject();
        json.addProperty("message", ex.getMessage());
        return json.toString();
    }
}
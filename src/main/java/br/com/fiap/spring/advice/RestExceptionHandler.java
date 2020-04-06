package br.com.fiap.spring.advice;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleGenericExceptions(Exception ex) {
        ResponseError responseError = new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro durante o processamento da requisição.");

        return ResponseEntity
                .status(responseError.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(getBody(responseError));
    }

    @ExceptionHandler(ResponseError.class)
    public final ResponseEntity<?> handleResponseErrorException(ResponseError ex) {
        return ResponseEntity
                .status(ex.getStatus())
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
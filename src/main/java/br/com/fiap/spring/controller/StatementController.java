package br.com.fiap.spring.controller;

import br.com.fiap.spring.advice.ResponseError;
import br.com.fiap.spring.dto.FilterReport;
import br.com.fiap.spring.dto.PaymentStatementResponse;
import br.com.fiap.spring.entity.Payment;
import br.com.fiap.spring.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static br.com.fiap.spring.utils.DateUtils.isInvalidPeriod;
import static br.com.fiap.spring.utils.DateUtils.toLocalDate;
import static br.com.fiap.spring.utils.DateUtils.convertToDate;

@RestController
@RequestMapping("/spring/v1/credit-card/statement")
@Api(value = "Gerenciamento de Extratos")
public class StatementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatementController.class);

    private PaymentService paymentService;

    private static final int HTTP_STATUS_OK = 200;

    @Autowired
    public StatementController(final PaymentService paymentService){
        this.paymentService = paymentService;
    };

    @ApiOperation(value = "Consultar extrato de pagamentos do estudante via cartão de crédito")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_STATUS_OK, message = "Extrato de pagamentos")
    })
    @GetMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<PaymentStatementResponse>> getStudentCreditCardPaymentStatement(FilterReport params){
        LOGGER.info("Getting student credit card payment statement ... ");

        final LocalDateTime startDate = toLocalDate(params.getStartDate()).atStartOfDay();
        final LocalDateTime endDate = toLocalDate(params.getEndDate()).atTime(LocalTime.MAX);

        if (isInvalidPeriod(startDate, endDate)){
            throw new ResponseError("O período informado é inválido. Por favor verifique os dias de início e término.");
        }

        List<Payment> payments = paymentService.getStudentCreditCardPaymentStatement(startDate,
                endDate, params.getStudentRegistration());

        return ResponseEntity.ok(toPaymentStatementResponse(payments));
    }

    private List<PaymentStatementResponse> toPaymentStatementResponse(List<Payment> payments) {
        List<PaymentStatementResponse> paymentStatementResponse = new ArrayList<>();
        payments.forEach(payment -> paymentStatementResponse.add(new PaymentStatementResponse(payment.getId(),
                payment.getOrderId(), payment.getTotalOrderAmount(), payment.getStatus().name(),
                convertToDate(payment.getCreationDate()), convertToDate(payment.getUpdateDate()))));

        return paymentStatementResponse;
    }
}
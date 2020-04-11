package br.com.fiap.spring.controller;

import br.com.fiap.spring.dto.PaymentRequest;
import br.com.fiap.spring.dto.PaymentResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/spring/v1/payment")
@Api(value = "Gerenciamento de Transações/Pagamento")
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    private PaymentService paymentService;

    private static final int HTTP_STATUS_OK = 200;

    @Autowired
    public PaymentController(final PaymentService paymentService){
        this.paymentService = paymentService;
    };

    @ApiOperation(value = "Processar pagamento do estudante via cartão de crédito")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_STATUS_OK, message = "Pagamento processado")
    })
    @PostMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<PaymentResponse> processStudentCreditCardPayment(
            @Valid @RequestBody PaymentRequest paymentRequest){
        LOGGER.info("Processing payment with student credit card ... ");

        Payment payment = paymentService.processStudentCreditCardPayment(paymentRequest);

        return ResponseEntity.ok(toPaymentResponse(payment));
    }

    private PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getStatus().name());
    }
}

package br.com.fiap.spring.controller;

import br.com.fiap.spring.dto.CreditCardRequest;
import br.com.fiap.spring.dto.PaymentRequest;
import br.com.fiap.spring.dto.PaymentResponse;
import br.com.fiap.spring.entity.Payment;
import br.com.fiap.spring.enums.PaymentStatus;
import br.com.fiap.spring.service.PaymentService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest {

    private MockMvc mvc;

    @Mock
    PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    private static Integer ORDER_ID = 1;
    private static Integer GENERIC_ID = 1;
    private static BigDecimal TOTAL_ORDER_AMOUNT = new BigDecimal(1);
    private static String STUDENT_REGISTRATION = "1234567";
    private static final String CARD_NUMBER = "4539711103420778";
    private static final int VERIFICATION_CODE = 123;
    private static CreditCardRequest CREDIT_CARD = new CreditCardRequest(CARD_NUMBER, VERIFICATION_CODE);

    @Test
    public void shouldProcessAStudentCreditCardPayment() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest(ORDER_ID, TOTAL_ORDER_AMOUNT, STUDENT_REGISTRATION, CREDIT_CARD);
        Payment payment = new Payment(GENERIC_ID, paymentRequest.getOrderId(), paymentRequest.getTotalOrderAmount(),
                paymentRequest.getStudentRegistration(), PaymentStatus.APPROVED, LocalDateTime.now(), LocalDateTime.now());

        when(paymentService.processStudentCreditCardPayment(any(PaymentRequest.class))).thenReturn(payment);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/spring/v1/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(paymentRequest));

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(GENERIC_ID)))
                .andExpect(jsonPath("$.status", is(PaymentStatus.APPROVED.name())));

    }
}

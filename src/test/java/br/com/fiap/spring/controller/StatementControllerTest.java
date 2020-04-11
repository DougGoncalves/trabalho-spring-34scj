package br.com.fiap.spring.controller;

import br.com.fiap.spring.entity.Payment;
import br.com.fiap.spring.enums.PaymentStatus;
import br.com.fiap.spring.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static br.com.fiap.spring.controller.config.RestExceptionHandlerConfig.createExceptionResolver;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatementControllerTest {

    private MockMvc mvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private StatementController statementController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(statementController)
                .setHandlerExceptionResolvers(createExceptionResolver())
                .addFilter(((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }))
                .build();
    }

    private static Integer ORDER_ID = 1;
    private static Integer GENERIC_ID = 1;
    private static BigDecimal TOTAL_ORDER_AMOUNT = new BigDecimal(1);
    private static String STUDENT_REGISTRATION = "1234567";

    @Test
    public void shouldGetStudentCreditCardPaymentStatement() throws Exception {
        Payment payment = new Payment(GENERIC_ID, ORDER_ID, TOTAL_ORDER_AMOUNT,
                STUDENT_REGISTRATION, PaymentStatus.APPROVED, LocalDateTime.now(), LocalDateTime.now());

        when(paymentService.getStudentCreditCardPaymentStatement(any(LocalDateTime.class),
                any(LocalDateTime.class), eq(STUDENT_REGISTRATION)))
                .thenReturn(Collections.singletonList(payment));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/spring/v1/credit-card/statement")
                .param("startDate", "10/04/2020")
                .param("endDate", "10/04/2020")
                .param("studentRegistration", STUDENT_REGISTRATION);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(GENERIC_ID)))
                .andExpect(jsonPath("$[0].orderId", is(ORDER_ID)))
                .andExpect(jsonPath("$[0].totalOrderAmount", is(TOTAL_ORDER_AMOUNT.intValue())))
                .andExpect(jsonPath("$[0].status", is(PaymentStatus.APPROVED.name())));
    }

    @Test
    public void shouldReturnErrorMessageWhenGetStudentCreditCardPaymentStatementWithInvalidPeriod() throws Exception {
        Payment payment = new Payment(GENERIC_ID, ORDER_ID, TOTAL_ORDER_AMOUNT,
                STUDENT_REGISTRATION, PaymentStatus.APPROVED, LocalDateTime.now(), LocalDateTime.now());

        when(paymentService.getStudentCreditCardPaymentStatement(any(LocalDateTime.class),
                any(LocalDateTime.class), eq(STUDENT_REGISTRATION)))
                .thenReturn(Collections.singletonList(payment));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/spring/v1/credit-card/statement")
                .param("startDate", "11/04/2020")
                .param("endDate", "10/04/2020")
                .param("studentRegistration", STUDENT_REGISTRATION);

        mvc.perform(request).andExpect(status().isUnprocessableEntity());
    }
}
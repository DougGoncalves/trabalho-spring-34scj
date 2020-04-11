package br.com.fiap.spring.controller;

import br.com.fiap.spring.advice.exceptions.PreRegistrationFailedException;
import br.com.fiap.spring.advice.exceptions.StudentCreditCardNotFoundException;
import br.com.fiap.spring.dto.StudentCreditCardRequest;
import br.com.fiap.spring.entity.StudentCreditCard;
import br.com.fiap.spring.service.StudentCreditCardService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Collections;

import static br.com.fiap.spring.controller.config.RestExceptionHandlerConfig.createExceptionResolver;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentCardControllerTest {

    private MockMvc mvc;

    @Mock
    private StudentCreditCardService studentCreditCardService;

    @InjectMocks
    private StudentCardController studentCardController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(studentCardController)
                .setHandlerExceptionResolvers(createExceptionResolver())
                .addFilter(((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }))
                .build();
    }


    private static Integer GENERIC_ID = 1;
    private static String STUDENT_REGISTRATION = "1234567";
    private static final String CARD_NUMBER = "4539711103420778";
    private static final int VERIFICATION_CODE = 123;
    private static String NAME = "FULL NAME";
    private static String COURSE = "001-01";
    private static String EXPIRATION_DATE = "04/23";

    @Test
    public void shouldGetAllStudentsCreditCardPaged() throws Exception {
        StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
                CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

        Page<StudentCreditCard> studentCreditCardList = new PageImpl<>(
                new ArrayList<>(Collections.singletonList(studentCreditCard)));

        when(studentCreditCardService.getAllStudentsCreditCard(any(Pageable.class))).thenReturn(studentCreditCardList);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/spring/v1/student/credit-card")
                .param("page", "1")
                .param("size", "10");

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasNext", is(Boolean.FALSE)))
                .andExpect(jsonPath("$.pageNumber", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.studentsCreditCard[0].id", is(GENERIC_ID)))
                .andExpect(jsonPath("$.studentsCreditCard[0].registration", is(STUDENT_REGISTRATION)))
                .andExpect(jsonPath("$.studentsCreditCard[0].name", is(NAME)))
                .andExpect(jsonPath("$.studentsCreditCard[0].course", is(COURSE)))
                .andExpect(jsonPath("$.studentsCreditCard[0].cardNumber", is(CARD_NUMBER)))
                .andExpect(jsonPath("$.studentsCreditCard[0].expirationDate", is(EXPIRATION_DATE)))
                .andExpect(jsonPath("$.studentsCreditCard[0].verificationCode", is(VERIFICATION_CODE)));
    }

    @Test
    public void shouldGetStudentCreditCardById() throws Exception {
        StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
                CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

        when(studentCreditCardService.getStudentCreditCardById(GENERIC_ID)).thenReturn(studentCreditCard);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/spring/v1/student/credit-card/{id}", GENERIC_ID);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(GENERIC_ID)))
                .andExpect(jsonPath("$.registration", is(STUDENT_REGISTRATION)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.course", is(COURSE)))
                .andExpect(jsonPath("$.cardNumber", is(CARD_NUMBER)))
                .andExpect(jsonPath("$.expirationDate", is(EXPIRATION_DATE)))
                .andExpect(jsonPath("$.verificationCode", is(VERIFICATION_CODE)));
    }

    @Test
    public void shouldDeleteStudentCreditCardById() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/spring/v1/student/credit-card/{id}", GENERIC_ID);

        mvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    public void shouldUpdateStudentCreditCardById() throws Exception {
        StudentCreditCardRequest studentCreditCardRequest = new StudentCreditCardRequest(STUDENT_REGISTRATION, NAME,
                COURSE, CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/spring/v1/student/credit-card/{id}", GENERIC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(studentCreditCardRequest));

        mvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    public void shouldCreateStudentCreditCard() throws Exception {
        StudentCreditCard studentCreditCard = new StudentCreditCard(GENERIC_ID, STUDENT_REGISTRATION, NAME, COURSE,
                CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

        StudentCreditCardRequest studentCreditCardRequest = new StudentCreditCardRequest(STUDENT_REGISTRATION, NAME,
                COURSE, CARD_NUMBER, EXPIRATION_DATE, VERIFICATION_CODE);

        when(studentCreditCardService.createStudentCreditCard(any(StudentCreditCardRequest.class)))
                .thenReturn(studentCreditCard);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/spring/v1/student/credit-card/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(studentCreditCardRequest));

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(String.format("/spring/v1/student/credit-card/%s", GENERIC_ID))))
                .andExpect(jsonPath("$.id", is(GENERIC_ID)))
                .andExpect(jsonPath("$.registration", is(STUDENT_REGISTRATION)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.course", is(COURSE)))
                .andExpect(jsonPath("$.cardNumber", is(CARD_NUMBER)))
                .andExpect(jsonPath("$.expirationDate", is(EXPIRATION_DATE)))
                .andExpect(jsonPath("$.verificationCode", is(VERIFICATION_CODE)));
    }

    @Test
    public void shouldProcessPreRegistation() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/spring/v1/student/credit-card/pre-registration");

        mvc.perform(request).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnInternalServerErrorWhenProcessPreRegistation() throws Exception {
        doThrow(PreRegistrationFailedException.class).when(studentCreditCardService).processPreRegistration();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/spring/v1/student/credit-card/pre-registration");

        ResultActions resultActions = mvc.perform(request);

        resultActions.andExpect(status().isInternalServerError());
        assertThat(resultActions.andReturn().getResponse().getContentAsString(), containsString("message"));
    }

    @Test
    public void shouldReturnPreConditionFaildeWhenDeleteStudentCreditCardById() throws Exception {
        doThrow(StudentCreditCardNotFoundException.class).when(studentCreditCardService).deleteStudentCreditCard(GENERIC_ID);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/spring/v1/student/credit-card/{id}", GENERIC_ID);

        ResultActions resultActions = mvc.perform(request);

        resultActions.andExpect(status().isPreconditionFailed());
        assertThat(resultActions.andReturn().getResponse().getContentAsString(), containsString("message"));
    }

    @Test
    public void shouldReturnBadRequestWhenUpdateStudentCreditCardById() throws Exception {
        StudentCreditCardRequest studentCreditCardRequest = new StudentCreditCardRequest(STUDENT_REGISTRATION, NAME,
                COURSE, "1234567812345678", EXPIRATION_DATE, VERIFICATION_CODE);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/spring/v1/student/credit-card/{id}", GENERIC_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(studentCreditCardRequest));

        ResultActions resultActions = mvc.perform(request);

        resultActions.andExpect(status().isBadRequest());
        assertThat(resultActions.andReturn().getResponse().getContentAsString(), containsString("message"));
    }
 }
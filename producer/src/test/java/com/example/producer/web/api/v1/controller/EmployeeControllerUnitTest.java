package com.example.producer.web.api.v1.controller;

import com.example.producer.service.EmployeeService;
import com.example.producer.service.dto.EmployeeCreationDto;
import com.example.producer.web.api.v1.mapper.EmployeeMapper;
import com.example.producer.web.api.v1.model.EmployeeCreationModel;
import com.example.producer.web.exception.RestExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.example.producer.web.WebConstants.V1_ENDPOINTS_URI;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerUnitTest {

    private static final String TEST_URI = V1_ENDPOINTS_URI + "/employees";

    private static final String VALID_NAME = "Alice";
    private static final String VALID_SURNAME = "Thompson";
    private static final BigDecimal VALID_WAGE = valueOf(5500.75);
    private static final LocalDateTime VALID_EVENT_TIME = parse("2012-04-23T18:25:43.511Z", ISO_DATE_TIME);
    private static final String WAY_TOO_LONG_STRING = "Windows talking painted pasture yet its express parties use. "
            + "Sure last upon he same as knew next.";

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private EmployeeController tested;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(tested)
                .setControllerAdvice(new RestExceptionHandler(messageSource))
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void shouldSuccessfullyCreateNewEmployee() throws Exception {
        EmployeeCreationModel model = createModel(VALID_NAME, VALID_SURNAME, VALID_WAGE, VALID_EVENT_TIME);
        when(employeeMapper.toDto(eq(model))).thenReturn(new EmployeeCreationDto());

        mockMvc.perform(post(TEST_URI)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(model)))
                .andExpect(status().isCreated());

        verify(employeeMapper).toDto(eq(model));
        verify(employeeService).create(any(EmployeeCreationDto.class));
    }

    @Test
    public void shouldNotCreateNewEmployeeInCaseOfNullParameters() throws Exception {
        shouldNotCreateNewEmployee(createModel(null, null, null, null));
    }

    @Test
    public void shouldNotCreateNewEmployeeInCaseOfEmptyStringParameters() throws Exception {
        shouldNotCreateNewEmployee(createModel("", "", VALID_WAGE, VALID_EVENT_TIME));
    }

    @Test
    public void shouldNotCreateNewEmployeeInCaseOfTooLongStringParameters() throws Exception {
        shouldNotCreateNewEmployee(createModel(WAY_TOO_LONG_STRING, WAY_TOO_LONG_STRING, VALID_WAGE, VALID_EVENT_TIME));
    }

    @Test
    public void shouldNotCreateNewEmployeeInCaseOfNegativeWageParameter() throws Exception {
        shouldNotCreateNewEmployee(createModel(VALID_NAME, VALID_SURNAME, valueOf(-5500.75), VALID_EVENT_TIME));
    }

    @Test
    public void shouldNotCreateNewEmployeeInCaseOfZeroWageParameter() throws Exception {
        shouldNotCreateNewEmployee(createModel(VALID_NAME, VALID_SURNAME, ZERO, VALID_EVENT_TIME));
    }

    @Test
    public void shouldNotCreateNewEmployeeInCaseOfTooLargeWageParameter() throws Exception {
        shouldNotCreateNewEmployee(createModel(VALID_NAME, VALID_SURNAME, valueOf(55000.75), VALID_EVENT_TIME));
    }

    private void shouldNotCreateNewEmployee(EmployeeCreationModel model) throws Exception {
        mockMvc.perform(post(TEST_URI)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(asJsonString(model)))
                .andExpect(status().isBadRequest());

        verify(employeeMapper, never()).toDto(eq(model));
        verify(employeeService, never()).create(any(EmployeeCreationDto.class));
    }

    private EmployeeCreationModel createModel(String name, String surname, BigDecimal wage, LocalDateTime eventTime) {
        EmployeeCreationModel model = new EmployeeCreationModel();

        model.setName(name);
        model.setSurname(surname);
        model.setWage(wage);
        model.setEventTime(eventTime);

        return model;
    }

    private String asJsonString(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

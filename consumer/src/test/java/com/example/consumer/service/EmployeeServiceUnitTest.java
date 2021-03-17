package com.example.consumer.service;

import com.example.consumer.persistence.entity.Employee;
import com.example.consumer.persistence.entity.WageRecord;
import com.example.consumer.persistence.repository.EmployeeRepository;
import com.example.consumer.service.dto.EmployeeCreationDto;
import com.example.consumer.service.mapper.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceUnitTest {

    private static final String ERROR_MESSAGE = "Some error message";

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private WageRecordService wageRecordService;

    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    @InjectMocks
    private EmployeeService tested;

    private WageRecord wageRecord;
    private EmployeeCreationDto employeeCreationDto;

    @BeforeEach
    public void setUp() {
        wageRecord = new WageRecord();
        wageRecord.setActive(TRUE);
        wageRecord.setEmployee(new Employee());

        employeeCreationDto = new EmployeeCreationDto();
        employeeCreationDto.setWage(ONE);
    }

    @Test
    public void shouldSuccessfullyCreateNewEmployee() {
        // given
        when(employeeMapper.toEntity(any(EmployeeCreationDto.class))).thenReturn(new Employee());
        when(wageRecordService.create(any(Employee.class), any(BigDecimal.class))).thenReturn(wageRecord);

        // when
        tested.create(employeeCreationDto);

        // then
        verify(employeeMapper).toEntity(any(EmployeeCreationDto.class));
        verify(wageRecordService).create(any(Employee.class), any(BigDecimal.class));
        verify(employeeRepository).save(employeeCaptor.capture());

        Employee employee = employeeCaptor.getValue();
        assertThat(employee.getWageRecords()).isNotEmpty();
        assertThat(employee.getWageRecords()).contains(wageRecord);
    }

    @Test
    public void shouldNotCreateEmployeeInCaseOfWageRecordCreationIssue() {
        // given
        when(employeeMapper.toEntity(any(EmployeeCreationDto.class))).thenReturn(new Employee());
        when(wageRecordService.create(any(Employee.class), any(BigDecimal.class)))
                .thenThrow(new IllegalArgumentException(ERROR_MESSAGE));


        // when
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tested.create(employeeCreationDto))
                .withMessage(ERROR_MESSAGE);

        // then
        verify(employeeMapper).toEntity(any(EmployeeCreationDto.class));
        verify(wageRecordService).create(any(Employee.class), any(BigDecimal.class));
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}

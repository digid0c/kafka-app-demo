package com.example.consumer.service;

import com.example.consumer.config.WageProperties;
import com.example.consumer.persistence.entity.Employee;
import com.example.consumer.persistence.entity.WageRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WageRecordServiceUnitTest {

    private static final BigDecimal BASE_WAGE = valueOf(5500.75);
    private static final LocalDateTime EVENT_TIME = parse("2012-04-23T18:25:43.511Z", ISO_DATE_TIME);
    private static final Integer TAX_PERCENT = 25;
    private static final Integer ANOTHER_TAX_PERCENT = 10;

    @Mock
    private WageProperties wageProperties;

    @InjectMocks
    private WageRecordService tested;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setLastModifiedAt(EVENT_TIME);
    }

    @Test
    public void shouldSuccessfullyCreateWageRecord() {
        // given
        when(wageProperties.getTaxPercent()).thenReturn(TAX_PERCENT);

        // when
        WageRecord wageRecord = tested.create(employee, BASE_WAGE);

        // then
        assertWageRecord(wageRecord);
        assertThat(wageRecord.getTaxPercent()).isEqualTo(TAX_PERCENT);
        assertThat(wageRecord.getTaxAddedWage()).isEqualTo(valueOf(1375.19));
        assertThat(wageRecord.getTotalWage()).isEqualTo(valueOf(6875.94));
    }

    @Test
    public void shouldSuccessfullyCreateAnotherWageRecord() {
        // given
        when(wageProperties.getTaxPercent()).thenReturn(ANOTHER_TAX_PERCENT);

        // when
        WageRecord wageRecord = tested.create(employee, BASE_WAGE);

        // then
        assertWageRecord(wageRecord);
        assertThat(wageRecord.getTaxPercent()).isEqualTo(ANOTHER_TAX_PERCENT);
        assertThat(wageRecord.getTaxAddedWage()).isEqualTo(valueOf(550.08));
        assertThat(wageRecord.getTotalWage()).isEqualTo(valueOf(6050.83));
    }

    private void assertWageRecord(WageRecord wageRecord) {
        assertThat(wageRecord).isNotNull();
        assertThat(wageRecord.getId()).isNull();
        assertThat(wageRecord.getEmployee()).isEqualTo(employee);
        assertThat(wageRecord.getBaseWage()).isEqualTo(BASE_WAGE);
        assertThat(wageRecord.getActive()).isTrue();
        assertThat(wageRecord.getCreatedAt()).isEqualTo(EVENT_TIME);
        assertThat(wageRecord.getLastModifiedAt()).isEqualTo(EVENT_TIME);
    }

    @Test
    public void shouldNotCreateWageRecordInCaseOfNullTaxPercent() {
        shouldNotCreateWageRecord(null);
    }

    @Test
    public void shouldNotCreateWageRecordInCaseOfNegativeTaxPercent() {
        shouldNotCreateWageRecord(-15);
    }

    @Test
    public void shouldNotCreateWageRecordInCaseOfTooLargeTaxPercent() {
        shouldNotCreateWageRecord(200);
    }

    private void shouldNotCreateWageRecord(Integer taxPercent) {
        // given
        when(wageProperties.getTaxPercent()).thenReturn(taxPercent);

        // when
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> tested.create(employee, BASE_WAGE))
                .withMessage("Tax percent is not defined or is out of range");
    }
}

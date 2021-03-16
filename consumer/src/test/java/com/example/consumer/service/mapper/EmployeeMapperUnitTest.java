package com.example.consumer.service.mapper;

import com.example.consumer.service.dto.EmployeeCreationDto;
import com.example.integration.dto.NewEmployeeCreatedEventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

public class EmployeeMapperUnitTest {

    private static final String NAME = "Alice";
    private static final String SURNAME = "Thompson";
    private static final BigDecimal WAGE = valueOf(5500.75);
    private static final LocalDateTime EVENT_TIME = parse("2012-04-23T18:25:43.511Z", ISO_DATE_TIME);

    private EmployeeMapper tested;

    @BeforeEach
    public void setUp() {
        tested = getMapper(EmployeeMapper.class);
    }

    @Test
    public void shouldSuccessfullyMapEventToDto() {
        // given
        NewEmployeeCreatedEventDto event = new NewEmployeeCreatedEventDto();
        event.setName(NAME);
        event.setSurname(SURNAME);
        event.setWage(WAGE);
        event.setEventTime(EVENT_TIME);

        // when
        EmployeeCreationDto dto = tested.toDto(event);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo(NAME);
        assertThat(dto.getSurname()).isEqualTo(SURNAME);
        assertThat(dto.getWage()).isEqualTo(WAGE);
        assertThat(dto.getEventTime()).isEqualTo(EVENT_TIME);
    }
}

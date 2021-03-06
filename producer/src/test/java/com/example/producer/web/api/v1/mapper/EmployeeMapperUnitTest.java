package com.example.producer.web.api.v1.mapper;

import com.example.integration.dto.NewEmployeeCreatedEventDto;
import com.example.producer.service.dto.EmployeeCreationDto;
import com.example.producer.web.api.v1.model.EmployeeCreationModel;
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
    public void shouldSuccessfullyMapModelToDto() {
        // given
        EmployeeCreationModel model = new EmployeeCreationModel();
        model.setName(NAME);
        model.setSurname(SURNAME);
        model.setWage(WAGE);
        model.setEventTime(EVENT_TIME);

        // when
        EmployeeCreationDto dto = tested.toDto(model);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo(NAME);
        assertThat(dto.getSurname()).isEqualTo(SURNAME);
        assertThat(dto.getWage()).isEqualTo(WAGE);
        assertThat(dto.getEventTime()).isEqualTo(EVENT_TIME);
    }

    @Test
    public void shouldSuccessfullyMapDtoToEvent() {
        // given
        EmployeeCreationDto dto = new EmployeeCreationDto();
        dto.setName(NAME);
        dto.setSurname(SURNAME);
        dto.setWage(WAGE);
        dto.setEventTime(EVENT_TIME);

        // when
        NewEmployeeCreatedEventDto event = tested.toEventDto(dto);

        // then
        assertThat(event).isNotNull();
        assertThat(event.getName()).isEqualTo(NAME);
        assertThat(event.getSurname()).isEqualTo(SURNAME);
        assertThat(event.getWage()).isEqualTo(WAGE);
        assertThat(event.getEventTime()).isEqualTo(EVENT_TIME);
    }

}

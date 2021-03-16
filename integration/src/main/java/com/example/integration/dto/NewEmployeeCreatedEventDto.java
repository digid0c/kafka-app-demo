package com.example.integration.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class NewEmployeeCreatedEventDto implements ApplicationEventDto {

    private String name;
    private String surname;
    private BigDecimal wage;
    private LocalDateTime eventTime;
}

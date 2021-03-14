package com.example.producer.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EmployeeCreationDto {

    private String name;
    private String surname;
    private BigDecimal wage;
    private LocalDateTime eventTime;
}

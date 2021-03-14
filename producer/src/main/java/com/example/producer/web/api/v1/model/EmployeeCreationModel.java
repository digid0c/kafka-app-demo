package com.example.producer.web.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Data
@Schema(description = "Employee creation model, used to map data from incoming requests")
public class EmployeeCreationModel {

    @NotBlank
    @Size(min = 1, max = 75)
    @Schema(description = "Employee first name")
    private String name;

    @NotBlank
    @Size(min = 1, max = 75)
    @Schema(description = "Employee last name")
    private String surname;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    @DecimalMax(value = "10000.00", inclusive = true)
    @Schema(description = "Employee initial wage")
    private BigDecimal wage;

    @NotNull
    @DateTimeFormat(iso = DATE_TIME)
    @Schema(description = "Actual employee creation date and time in UTC timezone")
    private LocalDateTime eventTime;
}

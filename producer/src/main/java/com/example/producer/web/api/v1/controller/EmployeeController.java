package com.example.producer.web.api.v1.controller;

import com.example.producer.service.EmployeeService;
import com.example.producer.web.api.v1.mapper.EmployeeMapper;
import com.example.producer.web.api.v1.model.EmployeeCreationModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.producer.web.WebConstants.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping(V1_ENDPOINTS_URI + "/employees")
@Tag(name = "Employees API", description = "Provides CRUD operations to manage employees entities")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(description = "Create new employee", responses = {
            @ApiResponse(responseCode = CREATED_CODE, description = "Returned in case employee creation is successful"),
            @ApiResponse(responseCode = BAD_REQUEST_CODE, description = "Returned in case parameters validation fails")
    })
    public void create(@Parameter(description = "Employee creation model", required = true)
                           @Valid @RequestBody EmployeeCreationModel employeeCreationModel) {
        employeeService.create(employeeMapper.toDto(employeeCreationModel));
    }
}

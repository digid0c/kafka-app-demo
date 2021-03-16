package com.example.consumer.service.mapper;

import com.example.consumer.service.dto.EmployeeCreationDto;
import com.example.integration.dto.NewEmployeeCreatedEventDto;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {

    EmployeeCreationDto toDto(NewEmployeeCreatedEventDto eventDto);
}

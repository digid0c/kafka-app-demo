package com.example.producer.web.api.v1.mapper;

import com.example.integration.dto.NewEmployeeCreatedEventDto;
import com.example.producer.service.dto.EmployeeCreationDto;
import com.example.producer.web.api.v1.model.EmployeeCreationModel;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {

    EmployeeCreationDto toDto(EmployeeCreationModel model);

    NewEmployeeCreatedEventDto toEventDto(EmployeeCreationDto dto);
}

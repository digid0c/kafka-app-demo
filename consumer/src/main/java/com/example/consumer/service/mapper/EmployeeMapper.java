package com.example.consumer.service.mapper;

import com.example.consumer.persistence.entity.Employee;
import com.example.consumer.service.dto.EmployeeCreationDto;
import com.example.integration.dto.NewEmployeeCreatedEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeeMapper {

    EmployeeCreationDto toDto(NewEmployeeCreatedEventDto eventDto);

    @Mapping(source = "eventTime", target = "createdAt")
    @Mapping(source = "eventTime", target = "lastModifiedAt")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "wageRecords", ignore = true)
    Employee toEntity(EmployeeCreationDto dto);
}

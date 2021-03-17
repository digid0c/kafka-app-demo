package com.example.consumer.service;

import com.example.consumer.persistence.entity.Employee;
import com.example.consumer.persistence.entity.WageRecord;
import com.example.consumer.persistence.repository.EmployeeRepository;
import com.example.consumer.service.dto.EmployeeCreationDto;
import com.example.consumer.service.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final WageRecordService wageRecordService;

    public void create(EmployeeCreationDto employeeCreationDto) {
        Employee newEmployee = employeeMapper.toEntity(employeeCreationDto);
        WageRecord initialWageRecord = wageRecordService.create(newEmployee, employeeCreationDto.getWage());
        newEmployee.getWageRecords().add(initialWageRecord);
        employeeRepository.save(newEmployee);
    }
}

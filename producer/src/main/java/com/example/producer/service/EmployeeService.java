package com.example.producer.service;

import com.example.producer.service.dto.EmployeeCreationDto;
import com.example.producer.service.publisher.NewEmployeeCreatedEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final NewEmployeeCreatedEventPublisher newEmployeeCreatedEventPublisher;

    /**
     * Processes received request to create new employee further to relevant event publisher.
     * @param employeeCreationDto employee details dto
     */
    public void create(EmployeeCreationDto employeeCreationDto) {
        newEmployeeCreatedEventPublisher.publish(employeeCreationDto);
    }
}

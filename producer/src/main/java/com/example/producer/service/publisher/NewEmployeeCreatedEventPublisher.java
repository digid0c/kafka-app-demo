package com.example.producer.service.publisher;

import com.example.integration.ApplicationEventPublisher;
import com.example.producer.service.dto.EmployeeCreationDto;
import com.example.producer.web.api.v1.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewEmployeeCreatedEventPublisher {

    private final EmployeeMapper employeeMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(EmployeeCreationDto employeeCreationDto) {
        log.info("Received request to publish new employee created event {}", employeeCreationDto);
        applicationEventPublisher.publish(employeeMapper.toEventDto(employeeCreationDto));
    }
}

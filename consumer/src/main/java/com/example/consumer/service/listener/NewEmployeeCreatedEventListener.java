package com.example.consumer.service.listener;

import com.example.consumer.service.EmployeeService;
import com.example.consumer.service.mapper.EmployeeMapper;
import com.example.integration.ApplicationEventListener;
import com.example.integration.dto.NewEmployeeCreatedEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewEmployeeCreatedEventListener extends ApplicationEventListener<NewEmployeeCreatedEventDto> {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Override
    public void handleEvent(NewEmployeeCreatedEventDto applicationEventDto) {
        log.info("Received request to handle new employee created event {}", applicationEventDto);
        employeeService.create(employeeMapper.toDto(applicationEventDto));
    }
}

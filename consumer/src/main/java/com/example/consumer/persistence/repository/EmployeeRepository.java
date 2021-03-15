package com.example.consumer.persistence.repository;

import com.example.consumer.persistence.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

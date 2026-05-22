package com.project.ems_backend.service;

import com.project.ems_backend.dto.EmployeeCreateRequest;
import com.project.ems_backend.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeCreateRequest request);

    EmployeeDto getEmployee(Long employeeId);

    List<EmployeeDto> getAllEmployee();

    EmployeeDto updateEmployee(Long id,EmployeeDto emp);

    void deleteEmployee(Long id);
}

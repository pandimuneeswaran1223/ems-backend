package com.project.ems_backend.mapper;

import com.project.ems_backend.dto.EmployeeDto;
import com.project.ems_backend.entity.Employee;

public class EmployeeMapper {

    public static Employee mapToEmployeeDto(EmployeeDto employeeDto)
    {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
    }

    public static EmployeeDto mapToEmployee(Employee employee)
    {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
    }
}

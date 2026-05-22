package com.project.ems_backend.mapper;

import com.project.ems_backend.dto.EmployeeDto;
import com.project.ems_backend.entity.Employee;

public class EmployeeMapper {

    public static Employee mapToEmployeeDto(EmployeeDto employeeDto)
    {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setPhone(employeeDto.getPhone());
        employee.setDesignation(employeeDto.getDesignation());
        employee.setDateOfJoining(employeeDto.getDateOfJoining());
        employee.setIsActive(employeeDto.getIsActive()!=null?employeeDto.getIsActive():true);
        return employee;
    }

    public static EmployeeDto mapToEmployee(Employee employee)
    {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setDepartment(employee.getDepartment());
        employeeDto.setPhone(employee.getPhone());
        employeeDto.setDesignation(employee.getDesignation());
        employeeDto.setDateOfJoining(employee.getDateOfJoining());
        employeeDto.setIsActive(employee.getIsActive());
        return employeeDto;
    }
}

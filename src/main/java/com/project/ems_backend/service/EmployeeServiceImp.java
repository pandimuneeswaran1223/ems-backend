package com.project.ems_backend.service;

import com.project.ems_backend.dto.EmployeeDto;
import com.project.ems_backend.entity.Employee;
import com.project.ems_backend.exception.ResourceNotFoundException;
import com.project.ems_backend.mapper.EmployeeMapper;
import com.project.ems_backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImp implements EmployeeService{

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee= EmployeeMapper.mapToEmployeeDto(employeeDto);
        Employee createEmployee=employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployee(createEmployee);
    }

    @Override
    public EmployeeDto getEmployee(Long employeeId) {
       Employee emp= employeeRepository.findById(employeeId)
               .orElseThrow(()->new ResourceNotFoundException("The given employee id "+employeeId+" not exist"));
        return EmployeeMapper.mapToEmployee(emp);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> allEmp=employeeRepository.findAll();
        return allEmp.stream().map(EmployeeMapper::mapToEmployee).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto emp) {
       Employee employee = employeeRepository.findById(id)
               .orElseThrow(()->new ResourceNotFoundException("The given employee id "+id+" not exist"));
       employee.setFirstName(emp.getFirstName());
       employee.setLastName(emp.getLastName());
       employee.setEmail(emp.getEmail());
       Employee updatedEmp=employeeRepository.save(employee);
       return EmployeeMapper.mapToEmployee(updatedEmp);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

}

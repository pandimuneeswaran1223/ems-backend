package com.project.ems_backend.service;

import com.project.ems_backend.dto.EmployeeCreateRequest;
import com.project.ems_backend.dto.EmployeeDto;
import com.project.ems_backend.dto.UsersDto;
import com.project.ems_backend.entity.Employee;
import com.project.ems_backend.entity.Users;
import com.project.ems_backend.exception.ResourceNotFoundException;
import com.project.ems_backend.mapper.EmployeeMapper;
import com.project.ems_backend.repository.EmployeeRepository;
import com.project.ems_backend.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImp implements EmployeeService{

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeCreateRequest request) {

        EmployeeDto employeeDto=request.getEmployeeDetails();
        UsersDto usersDto=request.getUserDetails();

        Employee employee= EmployeeMapper.mapToEmployeeDto(employeeDto);
        Users user=Users.builder()
                .userName(usersDto.getUserName())
                .password(passwordEncoder.encode(usersDto.getPassword()))
                .role(usersDto.getRole())
                .isPasswordReset(false)
                .isActive(true)
                .employee(employee)
                .build();
        employee.setUsers(user);
        Employee createEmployee=employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployee(createEmployee);
    }

    @Override
    public EmployeeDto getEmployee(Long employeeId) {
       Employee emp= employeeRepository.findByIdAndIsActiveTrue(employeeId)
               .orElseThrow(()->new ResourceNotFoundException("The given employee id "+employeeId+" not exist"));
        return EmployeeMapper.mapToEmployee(emp);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> allEmp=employeeRepository.findByIsActiveTrue();
        return allEmp.stream().map(EmployeeMapper::mapToEmployee).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto emp) {
       Employee employee = employeeRepository.findById(id)
               .orElseThrow(()->new ResourceNotFoundException("The given employee id "+id+" not exist"));
        employee.setFirstName(emp.getFirstName());
        employee.setLastName(emp.getLastName());
        employee.setEmail(emp.getEmail());
        employee.setDepartment(emp.getDepartment());
        employee.setPhone(emp.getPhone());
        employee.setDesignation(emp.getDesignation());
        employee.setDateOfJoining(emp.getDateOfJoining());
       Employee updatedEmp=employeeRepository.save(employee);
       return EmployeeMapper.mapToEmployee(updatedEmp);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee emp=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The given employee id " + id + " not exist"));
        emp.setIsActive(false);
        Users user=emp.getUsers();
       if(user!=null)
       {
           user.setIsActive(false);
           usersRepository.save(user);
       }
       employeeRepository.save(emp);
    }

}

package com.project.ems_backend.service;

import com.project.ems_backend.dto.EmployeeCreateRequest;
import com.project.ems_backend.dto.EmployeeDto;
import com.project.ems_backend.dto.UsersDto;
import com.project.ems_backend.entity.Employee;
import com.project.ems_backend.entity.Role;
import com.project.ems_backend.entity.Users;
import com.project.ems_backend.exception.ResourceNotFoundException;
import com.project.ems_backend.mapper.EmployeeMapper;
import com.project.ems_backend.repository.EmployeeRepository;
import com.project.ems_backend.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImp implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeCreateRequest request) {

        EmployeeDto employeeDto = request.getEmployeeDetails();
        UsersDto usersDto = request.getUserDetails();

        // Employee create pannrom
        Employee employee = EmployeeMapper.mapToEmployeeDto(employeeDto);

        // Password encode — only once!
        String encodedPassword = passwordEncoder.encode(usersDto.getPassword());

        // User create pannrom
        Users user = Users.builder()
                .userName(usersDto.getUserName())
                .password(encodedPassword)  // ← Single encode ✅
                .role(usersDto.getRole())
                .isPasswordReset(false)
                .isActive(true)
                .employee(employee)
                .build();

        // Link pannrom
        employee.setUsers(user);

        // Save pannrom
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployee(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployee(Long employeeId) {
        Employee emp = employeeRepository.findByIdAndIsActiveTrue(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "The given employee id " + employeeId + " not exist"));
        return EmployeeMapper.mapToEmployee(emp);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        List<Employee> allEmp = employeeRepository.findByIsActiveTrue();
        return allEmp.stream()
                .map(EmployeeMapper::mapToEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto emp) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "The given employee id " + id + " not exist"));

        // Manager ADMIN/MANAGER edit pannakoodathu!
        // Current user role check pannuvom
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentRole = auth.getAuthorities().stream()
                .findFirst()
                .map(a -> a.getAuthority())
                .orElse("");

        if (currentRole.equals("ROLE_MANAGER")) {
            // Employee user role check pannuvom
            Users user = employee.getUsers();
            if (user != null &&
                    (user.getRole() == Role.ADMIN || user.getRole() == Role.MANAGER)) {
                throw new RuntimeException(
                        "Managers can only edit EMPLOYEE role users!");
            }
        }

        employee.setFirstName(emp.getFirstName());
        employee.setLastName(emp.getLastName());
        employee.setEmail(emp.getEmail());
        employee.setDepartment(emp.getDepartment());
        employee.setPhone(emp.getPhone());
        employee.setDesignation(emp.getDesignation());
        employee.setDateOfJoining(emp.getDateOfJoining());

        Employee updatedEmp = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployee(updatedEmp);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "The given employee id " + id + " not exist"));

        // Soft delete
        emp.setIsActive(false);

        Users user = emp.getUsers();
        if (user != null) {
            user.setIsActive(false);
            usersRepository.save(user);
        }

        employeeRepository.save(emp);
    }
}
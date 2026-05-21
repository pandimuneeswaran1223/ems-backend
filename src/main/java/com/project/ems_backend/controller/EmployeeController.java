package com.project.ems_backend.controller;

import com.project.ems_backend.dto.EmployeeDto;
import com.project.ems_backend.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@EnableWebSecurity
@EnableMethodSecurity
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> g(@RequestBody EmployeeDto employeeDto)
    {
       EmployeeDto savedEmployee= employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<?> getEmployee(@PathVariable Long id)
    {
        EmployeeDto employeeDto=employeeService.getEmployee(id);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')")
    public ResponseEntity<?> getAllEmployee()
    {
       List<EmployeeDto> employeeDto=employeeService.getAllEmployee();
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id,@RequestBody EmployeeDto empDto)
    {
        EmployeeDto employeeDto=employeeService.updateEmployee(id,empDto);
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> deleteEmployee(@PathVariable Long id)
        {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

}


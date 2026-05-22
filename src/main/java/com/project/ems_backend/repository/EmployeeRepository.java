package com.project.ems_backend.repository;

import com.project.ems_backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    // Active employees mattum fetch pannrom
    List<Employee> findByIsActiveTrue();

    // Active employee by id fetch pannrom
    Optional<Employee> findByIdAndIsActiveTrue(Long id);
}

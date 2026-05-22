package com.project.ems_backend.config;

import com.project.ems_backend.entity.Employee;
import com.project.ems_backend.entity.Role;
import com.project.ems_backend.entity.Users;
import com.project.ems_backend.repository.EmployeeRepository;
import com.project.ems_backend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsersRepository usersRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Admin already irukka check pannrom
        if (usersRepository.findByUserName("admin").isEmpty()) {

            // Step 1: Admin employee create pannrom
            Employee adminEmployee = new Employee();
            adminEmployee.setFirstName("Admin");
            adminEmployee.setLastName("User");
            adminEmployee.setEmail("admin@company.com");
            adminEmployee.setDepartment("Management");
            adminEmployee.setPhone("9876543210");
            adminEmployee.setDesignation("System Admin");
            adminEmployee.setDateOfJoining(LocalDate.now());
            adminEmployee.setIsActive(true);

            // Step 2: Admin user create pannrom
            Users adminUser = Users.builder()
                    .userName("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .isPasswordReset(true)   // Admin reset pannakoodathu
                    .isActive(true)
                    .employee(adminEmployee)
                    .build();

            // Step 3: Link pannrom
            adminEmployee.setUsers(adminUser);

            // Step 4: Save pannrom
            employeeRepository.save(adminEmployee);

            System.out.println("Admin user created successfully!");
        } else {
            System.out.println("Admin user already exists!");
        }
    }
}
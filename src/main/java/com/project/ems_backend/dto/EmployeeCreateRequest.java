package com.project.ems_backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreateRequest {

    private EmployeeDto employeeDetails;
    private UsersDto userDetails;
}

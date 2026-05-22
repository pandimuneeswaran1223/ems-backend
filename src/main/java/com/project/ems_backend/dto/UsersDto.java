package com.project.ems_backend.dto;

import com.project.ems_backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

    private String userName;
    private String password;
    private Role role;
}

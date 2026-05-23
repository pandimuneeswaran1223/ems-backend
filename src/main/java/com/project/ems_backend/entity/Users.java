package com.project.ems_backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String  userName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)//db la string format la store pandrathuku use pandrom
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_password_reset")
    private Boolean isPasswordReset=false;

    @Column(name="is_active")
    private Boolean isActive=true;

    @OneToOne
    @JoinColumn(name = "employee_id",referencedColumnName = "id")
    private Employee employee;

    @Column(name="lost_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_at")
    private LocalDateTime createdAt= LocalDateTime.now();

}

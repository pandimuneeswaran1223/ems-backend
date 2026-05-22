package com.project.ems_backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name="department")
    private String department;
    @Column(name = "phone")
    private String phone;
    @Column(name="designation")
    private String designation;
    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;
    @Column(name="is_active")
    private Boolean isActive=true;
    @OneToOne(mappedBy = "employee",cascade = CascadeType.PERSIST)
    private Users users;
}

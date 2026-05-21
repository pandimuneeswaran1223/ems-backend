package com.project.ems_backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}

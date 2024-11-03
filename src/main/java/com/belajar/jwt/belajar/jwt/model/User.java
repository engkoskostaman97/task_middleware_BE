package com.belajar.jwt.belajar.jwt.model;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) 
    private String email;
    private String password;
}

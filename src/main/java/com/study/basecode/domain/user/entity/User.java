package com.study.basecode.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public User(String name, String email, String encodedPassword) {
        this.name = name;
        this.email = email;
        this.password = encodedPassword;
        this.role = UserRole.USER;
    }
}

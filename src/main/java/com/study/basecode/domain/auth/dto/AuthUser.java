package com.study.basecode.domain.auth.dto;

import lombok.Getter;

@Getter
public class AuthUser {

    private final Long id;
    private final String email;

    public AuthUser(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
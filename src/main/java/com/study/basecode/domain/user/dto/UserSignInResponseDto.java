package com.study.basecode.domain.user.dto;

import lombok.Getter;

@Getter
public class UserSignInResponseDto {

    private String token;

    public UserSignInResponseDto(String token) {
        this.token = token;
    }
}

package com.study.basecode.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveResponseDto {

    private String token;

    public UserSaveResponseDto(String token) {
        this.token = token;
    }
}

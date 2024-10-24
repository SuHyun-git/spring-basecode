package com.study.basecode.domain.auth.dto;

import lombok.Getter;

@Getter
public class SiginupResponseDto {

    private final String bearerToken;

    public SiginupResponseDto(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}

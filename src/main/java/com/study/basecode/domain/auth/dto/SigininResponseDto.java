package com.study.basecode.domain.auth.dto;

import lombok.Getter;

@Getter
public class SigininResponseDto {
    private final String bearerToken;

    public SigininResponseDto(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}

package com.study.basecode.domain.user.dto;

import lombok.Getter;

@Getter
public class UserSaveRequestDto {
    private String name;
    private String email;
    private String password;
    private String role;
}

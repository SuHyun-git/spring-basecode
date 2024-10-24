package com.study.basecode.domain.user.controller;

import com.study.basecode.domain.user.dto.UserSaveRequestDto;
import com.study.basecode.domain.user.dto.UserSaveResponseDto;
import com.study.basecode.domain.user.dto.UserSiginRequestDto;
import com.study.basecode.domain.user.dto.UserSignInResponseDto;
import com.study.basecode.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSaveResponseDto> signup(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        UserSaveResponseDto userSaveResponseDto = userService.signup(userSaveRequestDto);
        return ResponseEntity.ok().body(userSaveResponseDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserSignInResponseDto> signin(@RequestBody UserSiginRequestDto userSiginRequestDto) {
        UserSignInResponseDto userSignInResponseDto = userService.signin(userSiginRequestDto);
        return ResponseEntity.ok().header("Authorization", userSignInResponseDto.getToken()).body(userSignInResponseDto);
    }
}

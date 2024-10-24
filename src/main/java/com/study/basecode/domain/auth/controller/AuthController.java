package com.study.basecode.domain.auth.controller;

import com.study.basecode.domain.auth.dto.SigininRequestDto;
import com.study.basecode.domain.auth.dto.SigininResponseDto;
import com.study.basecode.domain.auth.dto.SiginupRequestDto;
import com.study.basecode.domain.auth.dto.SiginupResponseDto;
import com.study.basecode.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<SiginupResponseDto> signup(@RequestBody SiginupRequestDto siginupRequestDto) {
        return ResponseEntity.ok(authService.signup(siginupRequestDto));
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<SigininResponseDto> siginin(@RequestBody SigininRequestDto sigininRequestDto) {
        String token = authService.signin(sigininRequestDto).getBearerToken();
        return ResponseEntity.ok().header("Authorization",token).body(authService.signin(sigininRequestDto));
    }
}

package com.study.basecode.domain.user.controller;

import com.study.basecode.domain.user.dto.UserRequestDto;
import com.study.basecode.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserRequestDto userRequestDto) {
        String token = userService.login(userRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("token: " + token);
    }
}

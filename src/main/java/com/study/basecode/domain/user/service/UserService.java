package com.study.basecode.domain.user.service;

import com.study.basecode.domain.user.dto.UserSaveRequestDto;
import com.study.basecode.domain.user.dto.UserSaveResponseDto;
import com.study.basecode.domain.user.dto.UserSiginRequestDto;
import com.study.basecode.domain.user.dto.UserSignInResponseDto;
import com.study.basecode.domain.user.entity.User;
import com.study.basecode.domain.user.entity.UserRole;
import com.study.basecode.domain.user.repository.UserRepository;
import com.study.basecode.security.JwtUtil;
import com.study.basecode.security.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final JwtUtil jwtUtil;


    @Transactional
    public UserSaveResponseDto signup(UserSaveRequestDto userSaveRequestDto) {
        if(userRepository.existsByEmail(userSaveRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        String encodePassword = securityConfig.passwordEncoder().encode(userSaveRequestDto.getPassword());

        User user = new User(
                userSaveRequestDto.getName(),
                userSaveRequestDto.getEmail(),
                encodePassword,
                UserRole.of(userSaveRequestDto.getRole())
        );

        User saveUser = userRepository.save(user);

        String token = jwtUtil.createToken(saveUser.getId(), saveUser.getEmail(), saveUser.getRole());

        return new UserSaveResponseDto(token);
    }

    public UserSignInResponseDto signin(UserSiginRequestDto userSiginRequestDto) {

        User user = userRepository.findByEmail(userSiginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(!securityConfig.passwordEncoder().matches(userSiginRequestDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("로그인에 실패했습니다.");
        }

        String token = jwtUtil.createToken(user.getId(), user.getEmail(), user.getRole());

        return new UserSignInResponseDto(token);
    }
}

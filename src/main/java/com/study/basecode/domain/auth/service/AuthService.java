package com.study.basecode.domain.auth.service;

import com.study.basecode.domain.auth.dto.SigininRequestDto;
import com.study.basecode.domain.auth.dto.SigininResponseDto;
import com.study.basecode.domain.auth.dto.SiginupRequestDto;
import com.study.basecode.domain.auth.dto.SiginupResponseDto;
import com.study.basecode.domain.user.entity.User;
import com.study.basecode.domain.user.repository.UserRepository;
import com.study.basecode.security.JwtUtil;
import com.study.basecode.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SiginupResponseDto signup(SiginupRequestDto siginupRequestDto) {

        if(userRepository.existsByEmail(siginupRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(siginupRequestDto.getPassword());

        User newUser = new User(siginupRequestDto.getName(), siginupRequestDto.getEmail(), encodedPassword);

        User savedUser = userRepository.save(newUser);

        String bearerToken = jwtUtil.createToken(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );

        return new SiginupResponseDto(bearerToken);
    }

    public SigininResponseDto signin(SigininRequestDto sigininRequestDto) {
        User user = userRepository.findByEmail(sigininRequestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("이메일을 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(sigininRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        String bearerToken = jwtUtil.createToken(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );

        return new SigininResponseDto(bearerToken);
    }
}

package com.army.ardiary.service;

import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.LoginResponseDto;
import com.army.ardiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public LoginResponseDto signUp(String email) {

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .nickname(email)
                .followingNum(0)
                .followerNum(0)
                .build();

        userRepository.insert(userEntity);
        UserEntity newUser = userRepository.selectByEmail(email);
        String token = tokenService.createToken(newUser.getUserId());
        String refreshToken = tokenService.createRefreshToken();
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
        return loginResponseDto;

    }

    public LoginResponseDto login(String email) {
        UserEntity loginUser = userRepository.selectByEmail(email);
        if (loginUser == null)
            return null;
        String token = tokenService.createToken(loginUser.getUserId());
        String refreshToken = tokenService.createRefreshToken();
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
        return loginResponseDto;
    }
}
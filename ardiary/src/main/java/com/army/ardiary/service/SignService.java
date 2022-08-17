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
        String id = Integer.toString(newUser.getUserId());
        String token = tokenService.createToken(id); //토큰 발급
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .jwt(token)
                .build();
        return loginResponseDto;

    }

    public LoginResponseDto login(String email) {
        UserEntity loginUser = userRepository.selectByEmail(email);
        if (loginUser == null)
            return null;
        String id = Integer.toString(loginUser.getUserId());
        String token = tokenService.createToken(id); //토큰 발급
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .jwt(token)
                .build();
        return loginResponseDto;
    }
}
package com.army.ardiary.service;

import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.LoginResponseDto;
import com.army.ardiary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SignService {
    private UserRepository userRepository;

    private TokenService tokenService;

    @Autowired
    SignService(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Autowired
        //생성자 주입
    SignService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public UserEntity getNewUser(String email){
        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .nickname(email)
                .followingNum(0)
                .followerNum(0)
                .build();

        userRepository.insert(userEntity);
        UserEntity newUser = userRepository.selectByEmail(email);
        return newUser;
    }

    public LoginResponseDto getLoginResponseDto(String email){
        UserEntity user = userRepository.selectByEmail(email); // 해당 email 을 가진 user 객체
        String token = tokenService.createToken(email); //토큰 발급
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .userEntity(user)
                .jwt(token)
                .build();
        return loginResponseDto;
    }
}
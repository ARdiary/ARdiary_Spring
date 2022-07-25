package com.army.ardiary.service;

import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.LoginResponseDto;
import com.army.ardiary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignService {
    private UserRepository userRepository;
    private TokenService tokenService;

    @Autowired
    SignService(TokenService tokenService, UserRepository userRepository){
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }
    //해당 email을 갖는 user 생성
    public UserEntity createNewUser(String email){
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

    //토큰생성 후 로그인 responseDto 반환
    public LoginResponseDto getLoginResponseDto(String email){
        UserEntity user = userRepository.selectByEmail(email);
        String id = Integer.toString(user.getUserId());
        String token = tokenService.createToken(id); //토큰 발급
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .jwt(token)
                .build();
        return loginResponseDto;
    }

    //email을 갖는 user의 존재여부 확인
    public boolean existUserByEmail(String email){
        if (userRepository.selectByEmail(email)!=null)
            return true;
        else
            return false;
    }
}
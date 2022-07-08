package com.army.ardiary.controller;


import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.LoginResponseDto;
import com.army.ardiary.jwt.JwtProvider;
import com.army.ardiary.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
public class LoginController {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    @Autowired //생성자 주입
    LoginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Autowired
    LoginController(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }

    //클라이언트가 email을 쿼리파라미터로 보내준 경우
    @PostMapping("/api/login")
    public ResponseEntity login(@RequestParam("email") String email){
        //user테이블에 해당 email을 가진 user가 있는지 확인
        if (isEmpty(userRepository.selectByEmail(email))){ //Empty인지 NULL인지 확인해보기
            //없으면 user테이블에 추가 후, 상태코드 201, user객체 응답
            UserEntity userEntity = UserEntity.builder()
                            .email(email)
                            .nickname(email)
                            .followingNum(0)
                            .followerNum(0)
                            .build();

            userRepository.insert(userEntity);
            UserEntity newUser = userRepository.selectByEmail(email);
            return new ResponseEntity(newUser,HttpStatus.CREATED);

        }else {
            //있으면 로그인 성공 200, User객체 (메일, 닉네임, 개인프로필이미지) 응답.
            UserEntity user = userRepository.selectByEmail(email); // 해당 email을 가진 user객체
            String token = jwtProvider.createToken(email); //토큰 발급
            LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                    .userEntity(user)
                    .jwt(token)
                    .build();
            //response body에 담을 내용: user에 대한 정보, 토큰
            return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
        }
    }
}

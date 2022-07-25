package com.army.ardiary.controller;

import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.EmailRequestDto;
import com.army.ardiary.dto.LoginResponseDto;
import com.army.ardiary.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SignController {
    // SignUp(회원가입) SignIn(로그인)API를 다루는 컨트롤러

    // signService 생성자 주입
    private SignService signService;
    @Autowired
    SignController(SignService signService){
        this.signService = signService;
    }

    // 회원가입 요청
    @PostMapping("/api/signup")
    public ResponseEntity join(@RequestBody @Valid EmailRequestDto emailRequestDto){
        String email = emailRequestDto.getEmail();
        // 받은 이메일을 가진 새 user db에 추가
        UserEntity user = signService.createNewUser(email);
        String id = Integer.toString(user.getUserId());
        // 토큰을 발급해 로그인 responsebody에 담을 데이터 준비.
        LoginResponseDto loginResponseDto = signService.getLoginResponseDto(id);
        return new ResponseEntity(loginResponseDto, HttpStatus.CREATED);// 회원가입 성공
    }

    // 로그인 요청
    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody @Valid EmailRequestDto emailRequestDto){
        String email = emailRequestDto.getEmail();
        //해당 email을 가진 user있는지 확인.
        if(!signService.existUserByEmail(email)){ //DB에 user가 없는 경우
            return new ResponseEntity("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);// 로그인 실패
        }
        //토큰을 발급해 로그인 responsebody에 담을 데이터 준비.
        LoginResponseDto loginResponseDto = signService.getLoginResponseDto(email);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);// 로그인 성공
    }
}
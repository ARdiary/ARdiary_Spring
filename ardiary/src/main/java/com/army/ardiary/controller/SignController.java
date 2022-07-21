package com.army.ardiary.controller;

import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.LoginResponseDto;
import com.army.ardiary.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignController {
    //SignUp(회원가입) SignIn(로그인)API를 다루는 컨트롤러

    //signService 생성자주입
    private SignService signService;
    @Autowired
    SignController(SignService signService){
        this.signService = signService;
    }

    //회원가입 요청
    @PostMapping("/api/signup")
    public ResponseEntity join(@RequestParam("email") String email){
        UserEntity newUser = signService.getNewUser(email);
        return new ResponseEntity(newUser, HttpStatus.CREATED);
    }

    //로그인 요청
    @PostMapping("/api/login")
    public ResponseEntity login(@RequestParam("email") String email){
        LoginResponseDto loginResponseDto = signService.getLoginResponseDto(email);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }



}
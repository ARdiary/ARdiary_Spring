package com.army.ardiary.controller;

import com.army.ardiary.dto.EmailRequestDto;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.LoginResponseDto;
import com.army.ardiary.service.SignService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SignController {
    // SignUp(회원가입) SignIn(로그인)API를 다루는 컨트롤러

    // signService 생성자 주입
    private final SignService signService;

    // 회원가입 요청
    @PostMapping("/api/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid EmailRequestDto emailRequestDto){

        String email = emailRequestDto.getEmail();
        LoginResponseDto loginResponseDto = signService.signUp(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponseDto);// 회원가입 성공
    }
    // 로그인 요청
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody @Valid EmailRequestDto emailRequestDto){

        String email = emailRequestDto.getEmail();
        LoginResponseDto loginResponseDto = signService.login(email);
        if (loginResponseDto==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("회원가입 필요"));
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDto);// 로그인 성공
    }
}
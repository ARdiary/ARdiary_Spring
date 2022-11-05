package com.army.ardiary.controller;

import com.army.ardiary.dto.DiaryDto;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.FollowDto;
import com.army.ardiary.service.TokenService;
import com.army.ardiary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final TokenService tokenService;
    private final UserService userService;

    @GetMapping("/api/user/following")
    public ResponseEntity<?> loadFollowingList(@RequestHeader(value = "Authorization") String headerToken){
        String token = headerToken.substring("Bearer ".length());
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        List<FollowDto> followings = userService.findFollowingList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(followings);
    }

    @GetMapping("/api/user/follower")
    public ResponseEntity<?> loadFollowerList(@RequestHeader(value = "Authorization") String headerToken){
        String token = headerToken.substring("Bearer ".length());
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        List<FollowDto> followers = userService.findFollowerList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(followers);
    }

    @GetMapping("/api/likes/diaries")
    public ResponseEntity<?> loadLikeDiaryList(@RequestHeader(value = "Authorization") String headerToken){
        String token = headerToken.substring("Bearer ".length());
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        List<DiaryDto> diaryDtos = userService.findLikeDiaryList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryDtos);
    }
}

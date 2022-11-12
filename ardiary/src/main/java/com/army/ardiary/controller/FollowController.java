package com.army.ardiary.controller;

import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.FollowRequestDto;
import com.army.ardiary.service.FollowService;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final TokenService tokenService;
    private final FollowService followService;

    @PostMapping("/api/follow")
    public ResponseEntity<?> addFollow(@RequestHeader(value = "Authorization") String headerToken, @RequestBody FollowRequestDto followRequestDto){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = tokenService.findUserIdByJwt(token);
        if (token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));

        int followeeId = followRequestDto.getFollowee();
        if (followService.isFollow(userId,followeeId)){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 팔로우 하고 있습니다");
        }
        followService.addFollow(userId, followeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body("팔로우 연결 완료");
    }

    @DeleteMapping("/api/follow/{id}")
    public ResponseEntity<?> deleteFollow(@RequestHeader(value = "Authorization") String headerToken, @PathVariable int id){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        if(token == null|| !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));

        followService.deleteFollow(id);
        return ResponseEntity.status(HttpStatus.OK).body("팔로우 해제 완료");
    }
}

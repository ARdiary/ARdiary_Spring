package com.army.ardiary.controller;

import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.FollowRequestDto;
import com.army.ardiary.service.FollowService;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final TokenService tokenService;
    private final FollowService followService;
    @PostMapping("/api/follow")
    public ResponseEntity<?> addFollow(@RequestHeader(value = "Autorization") String headerToken, @RequestBody FollowRequestDto followRequestDto){
        String token = headerToken.substring("Bearer ".length());
        int userId = tokenService.findUserIdByJwt(token);
        if (token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));

        int followeeId = followRequestDto.getFollowee();
        followService.addFollow(userId, followeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body("팔로우 연결성공");
    }
}

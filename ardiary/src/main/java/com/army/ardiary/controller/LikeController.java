package com.army.ardiary.controller;

import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.service.LikeService;
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
public class LikeController {

    private final TokenService tokenService;
    private final LikeService likeService;

    @PostMapping("/api/likes/diaries")
    public ResponseEntity<?> addLike(@RequestHeader(value = "Authorization")String headerToken, @RequestBody int id){
        String token = headerToken.substring("Bearer ".length());
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null|| !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        likeService.addLikeDiary(userId, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("일기 좋아요 추가");
    }

}

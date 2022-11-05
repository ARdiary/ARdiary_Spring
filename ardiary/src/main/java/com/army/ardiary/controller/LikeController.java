package com.army.ardiary.controller;

import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.service.LikeService;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final TokenService tokenService;
    private final LikeService likeService;

    @PostMapping("/api/likes/diaries")
    public ResponseEntity<?> addLikeDiary(@RequestHeader(value = "Authorization")String headerToken, @RequestBody int id){
        String token;
        String prefix = headerToken.substring(0, "Bearer ".length());
        if(prefix == "Bearer "){
            token = headerToken.substring("Bearer ".length());
        }else{
            token = headerToken;
        }
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null|| !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        likeService.addLikeDiary(userId, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("일기 좋아요 추가");
    }

    @DeleteMapping("/api/likes/diaries/{id}")
    public ResponseEntity<?> deleteLikeDiary(@RequestHeader(value = "Authorization")String headerToken, @PathVariable int id){
        String token;
        String prefix = headerToken.substring(0, "Bearer ".length());
        if(prefix == "Bearer "){
            token = headerToken.substring("Bearer ".length());
        }else{
            token = headerToken;
        }
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null|| !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        likeService.deleteLikeDiary(id);
        return ResponseEntity.status(HttpStatus.OK).body("일기 좋아요 삭제");
    }

    @PostMapping("/api/likes/guestbooks")
    public ResponseEntity<?> addLikeGuestBook(@RequestHeader(value = "Authorization")String headerToken, @RequestBody int id){
        String token;
        String prefix = headerToken.substring(0, "Bearer ".length());
        if(prefix == "Bearer "){
            token = headerToken.substring("Bearer ".length());
        }else{
            token = headerToken;
        }
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null|| !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        likeService.addLikeGuestBook(userId, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("방명록 좋아요 추가");
    }

    @DeleteMapping("/api/likes/guestbooks/{id}")
    public ResponseEntity<?> deleteLikeGuestBook(@RequestHeader(value = "Authorization") String headerToken, @PathVariable int id){
        String token;
        String prefix = headerToken.substring(0, "Bearer ".length());
        if(prefix == "Bearer "){
            token = headerToken.substring("Bearer ".length());
        }else{
            token = headerToken;
        }
        if(token == null|| !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        likeService.deleteLikeGuestBook(id);
        return ResponseEntity.status(HttpStatus.OK).body("방명록 좋아요 삭제");
    }

}

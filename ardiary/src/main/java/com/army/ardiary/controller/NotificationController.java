package com.army.ardiary.controller;

import com.army.ardiary.domain.entity.NotificationEntity;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.service.NotificationService;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final TokenService tokenService;
    private final NotificationService notificationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") int notificationId){
        NotificationEntity notificationEntity=notificationService.findById(notificationId);
        return ResponseEntity.ok(notificationEntity);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable(value = "id") int notificationId, @RequestHeader(value = "Authorization") String headerToken){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        //토큰 유효성 확인
        //토큰을 통해 사용자 정보 불러오기
        int userId = tokenService.findUserIdByJwt(token);
        if (token == null || !tokenService.validateToken(token)||!notificationService.isUser(userId, notificationId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok("삭제 완료");
    }

}

package com.army.ardiary.controller;

import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.TimeCapsuleDto;
import com.army.ardiary.dto.TimeCapsuleRequestDto;
import com.army.ardiary.service.TimeCapsuleService;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TimeCapsuleController {

    private final TokenService tokenService;
    private final TimeCapsuleService timeCapsuleService;
    @PostMapping("/api/timecapsules")
    public ResponseEntity<?> writeTimeCapsule(@RequestHeader(value = "Authorization") String headerToken, @RequestBody TimeCapsuleDto timeCapsuleDto) {
        String token = headerToken.substring("Bearer ".length());
        if (token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 작성 권한이 없습니다."));
        int userId = tokenService.findUserIdByJwt(token);
        TimeCapsuleRequestDto timeCapsuleRequestDto = timeCapsuleService.createTimeCapsule(userId, timeCapsuleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(timeCapsuleRequestDto);
    }

    @GetMapping("/api/timecapsules/{id}")
    public ResponseEntity<?> loadTimeCapsule(@RequestHeader(value = "Authorization") String headerToken, @PathVariable int id){
        String token = headerToken.substring("Bearer ".length());
        if (token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 작성 권한이 없습니다."));
        TimeCapsuleRequestDto timeCapsuleRequestDto = timeCapsuleService.findTimeCapsule(id);
        return ResponseEntity.status(HttpStatus.OK).body(timeCapsuleRequestDto);
    }
}

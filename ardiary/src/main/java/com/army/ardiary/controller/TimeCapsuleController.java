package com.army.ardiary.controller;

import com.army.ardiary.dto.DiaryDto;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.TimeCapsuleRequestDto;
import com.army.ardiary.dto.TimeCapsuleResponseDto;
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
    public ResponseEntity<?> writeTimeCapsule(@RequestHeader(value = "Authorization") String headerToken, @RequestBody TimeCapsuleRequestDto timeCapsuleRequestDto) {
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        if (token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 작성 권한이 없습니다."));
        int userId = tokenService.findUserIdByJwt(token);
        int newId = timeCapsuleService.createTimeCapsule(userId, timeCapsuleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newId);
    }

    @GetMapping("/api/timecapsules/{id}")
    public ResponseEntity<?> loadTimeCapsule(@RequestHeader(value = "Authorization") String headerToken, @PathVariable int id){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        if (token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 작성 권한이 없습니다."));
        TimeCapsuleResponseDto timeCapsuleResponseDto = timeCapsuleService.findTimeCapsule(id);
        return ResponseEntity.status(HttpStatus.OK).body(timeCapsuleResponseDto);
    }

    @GetMapping("/api/timecapsules/marker")
    public ResponseEntity<?> loadDiaryByMarker(@RequestParam("id") int markerId){
        TimeCapsuleResponseDto timeCapsuleResponseDto = timeCapsuleService.findByMarker(markerId);
        return ResponseEntity.status(HttpStatus.OK).body(timeCapsuleResponseDto);
    }

    @DeleteMapping("/api/timecapsules/{id}")
    public ResponseEntity<?> deleteTimecapsule(@RequestHeader(value = "Authorization") String headerToken, @PathVariable int id) {
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        if (token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 작성 권한이 없습니다."));
        timeCapsuleService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("타임캡슐 삭제완료");
    }
}

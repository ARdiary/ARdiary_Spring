package com.army.ardiary.controller;

import com.army.ardiary.domain.entity.GuestBookEntity;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.GuestBookContentDto;
import com.army.ardiary.service.GuestBookService;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class GuestBookController {

    private final TokenService tokenService;
    public final GuestBookService guestBookService;

    @PostMapping("/api/guestbooks")
    public ResponseEntity<?> writeGuestBook(@RequestHeader(value = "Authorization", required = false) String headerToken, @RequestBody GuestBookContentDto content){
        String token = headerToken.substring("Bearer ".length());
        if(token == null|| !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 작성 권한이 없습니다."));

        int userId = tokenService.findUserIdByJwt(token);

        GuestBookEntity newGuestBook = guestBookService.createGuestBook(userId, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGuestBook);
    }

    @GetMapping("/api/guestbooks/{id}")
    public ResponseEntity<?> loadGuestBook(@PathVariable("id") int id){
        GuestBookEntity guestBookEntity = guestBookService.findGuestBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(guestBookEntity);
    }

    @GetMapping("/api/guestbooks")
    public ResponseEntity<?> loadGuestBookListByUser(@RequestHeader(value = "Authorization") String headerToken) {
        String token = headerToken.substring("Bearer ".length());
        int userId = tokenService.findUserIdByJwt(token);
        if (token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));
        ArrayList<GuestBookEntity> guestBookEntities = guestBookService.findGuestBookListByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(guestBookEntities);
    }

    @PatchMapping("/api/guestbooks/{id}/content")
    public ResponseEntity<?> modifyContent(@RequestHeader(value = "Authorization") String headerToken, @PathVariable("id") int guestBookId, @RequestBody GuestBookContentDto newContent){
        String token = headerToken.substring("Bearer ".length());
        int userId = tokenService.findUserIdByJwt(token);
        if (token == null || !tokenService.validateToken(token)||!guestBookService.isUser(userId, guestBookId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));
        GuestBookEntity guestBookEntity = guestBookService.updateContent(guestBookId, newContent.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(guestBookEntity);
    }

    @DeleteMapping("/api/guestbooks/{id}")
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String headerToken, @PathVariable("id") int guestBookId) {
        String token = headerToken.substring("Bearer ".length());
        int userId = tokenService.findUserIdByJwt(token);
        if (token == null || !tokenService.validateToken(token)||!guestBookService.isUser(userId, guestBookId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));
        GuestBookEntity deleted = guestBookService.delete(guestBookId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }
}

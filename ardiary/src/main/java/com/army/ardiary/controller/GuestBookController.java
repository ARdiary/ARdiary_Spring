package com.army.ardiary.controller;

import com.army.ardiary.domain.entity.GuestBookEntity;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.GuestBookInfoDto;
import com.army.ardiary.service.GuestBookService;
import com.army.ardiary.service.TokenService;
import io.jsonwebtoken.Claims;
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
    public ResponseEntity<?> writeGuestBook(@RequestHeader(value = "Authorization", required = false) String headerToken, @RequestBody GuestBookInfoDto guestBookInfo){
        String token = headerToken.substring("Bearer ".length());
        System.out.println(token);
        if(token == null|| !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 작성 권한이 없습니다."));

        int userId = tokenService.findUserIdByJwt(token);

        GuestBookEntity newGuestBook = guestBookService.createGuestBook(userId, guestBookInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGuestBook);
    }

    @GetMapping("/api/guestbooks/{id}")
    public ResponseEntity<?> findGuestBook(@PathVariable("id") int id){
        GuestBookEntity guestBookEntity = guestBookService.findGuestBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(guestBookEntity);
    }

    @GetMapping("/api/guestbooks")
    public ResponseEntity<?> findGuestBookListByUser(@RequestHeader(value = "Authorization") String token) {
        if (token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));

        Claims claims = tokenService.getJwtContents(token);
        int userId = Integer.parseInt(claims.getSubject());

        ArrayList<GuestBookEntity> guestBookEntities = guestBookService.findGuestBookListByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(guestBookEntities);
    }
}
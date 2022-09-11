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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GuestBookController {

    private final TokenService tokenService;
    public final GuestBookService guestBookService;

    @PostMapping("/api/guestbooks")
    public ResponseEntity<?> writeGuestBook(@RequestHeader(value = "Authorization") String token, @RequestBody GuestBookInfoDto guestBookInfo){

        if(token == null|| !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("작성 권한이 없습니다."));

        Claims claims = tokenService.getJwtContents(token);
        int userId = Integer.parseInt(claims.getSubject());

        GuestBookEntity newGuestBook = guestBookService.createGuestBook(userId, guestBookInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGuestBook);
    }
}

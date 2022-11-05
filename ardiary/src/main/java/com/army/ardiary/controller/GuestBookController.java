package com.army.ardiary.controller;

import com.army.ardiary.domain.entity.GuestBookEntity;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.GuestBookContentDto;
import com.army.ardiary.dto.GuestBookDto;
import com.army.ardiary.service.GuestBookService;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GuestBookController {

    private final TokenService tokenService;
    public final GuestBookService guestBookService;

    @PostMapping("/api/guestbooks")
    public ResponseEntity<?> writeGuestBook(@RequestHeader(value = "Authorization", required = false) String headerToken, @RequestBody GuestBookContentDto content){
        String token;
        String prefix = headerToken.substring(0, "Bearer ".length());
        if(prefix == "Bearer "){
            token = headerToken.substring("Bearer ".length());
        }else{
            token = headerToken;
        }
        if(token == null|| !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 작성 권한이 없습니다."));

        int userId = tokenService.findUserIdByJwt(token);

        GuestBookDto newGuestBook = guestBookService.createGuestBook(userId, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGuestBook);
    }

    @GetMapping("/api/guestbooks/{id}")
    public ResponseEntity<?> loadGuestBook(@PathVariable("id") int id){
        GuestBookDto guestBookDto = guestBookService.findGuestBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(guestBookDto);
    }

    @GetMapping("/api/guestbooks")
    public ResponseEntity<?> loadGuestBookListByUser(@RequestHeader(value = "Authorization") String headerToken) {
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = tokenService.findUserIdByJwt(token);
        if (token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));
        List<GuestBookDto> guestBookDtoList = guestBookService.findGuestBookListByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(guestBookDtoList);
    }

    @PatchMapping("/api/guestbooks/{id}/content")
    public ResponseEntity<?> modifyContent(@RequestHeader(value = "Authorization") String headerToken, @PathVariable("id") int guestBookId, @RequestBody GuestBookContentDto newContent){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = tokenService.findUserIdByJwt(token);
        if (token == null || !tokenService.validateToken(token)||!guestBookService.isUser(userId, guestBookId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));
        GuestBookDto guestBookDto = guestBookService.updateContent(guestBookId, newContent.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(guestBookDto);
    }

    @DeleteMapping("/api/guestbooks/{id}")
    public ResponseEntity<?> delete(@RequestHeader(value = "Authorization") String headerToken, @PathVariable("id") int guestBookId) {
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = tokenService.findUserIdByJwt(token);
        if (token == null || !tokenService.validateToken(token)||!guestBookService.isUser(userId, guestBookId))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));
        GuestBookDto deleted = guestBookService.delete(guestBookId);
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

    @GetMapping("/api/guestbooks/marker")
    public ResponseEntity<?> loadDiaryByMarker(@RequestParam("id") int markerId){
        GuestBookDto guestBookDto = guestBookService.findByMarker(markerId);
        return ResponseEntity.status(HttpStatus.OK).body(guestBookDto);
    }
}

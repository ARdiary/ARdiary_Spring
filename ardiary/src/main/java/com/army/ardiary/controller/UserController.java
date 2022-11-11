package com.army.ardiary.controller;

import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.DiaryDto;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.FollowDto;
import com.army.ardiary.dto.GuestBookDto;
import com.army.ardiary.service.TokenService;
import com.army.ardiary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final TokenService tokenService;
    private final UserService userService;

    @GetMapping("/api/users/nickname")
    public ResponseEntity<?> checkUserByNickName(@RequestParam String nickname){
        int result = userService.getUserByNickName(nickname);
        if (result==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 nickname을 가진 user가 존재하지 않습니다. (닉네임 사용 가능)");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body("해당 nickname을 가진 user의 id는 "+result+"입니다. (닉네임 사용 불가)");
        }
    }

    @PutMapping("/api/users/nickname")
    public ResponseEntity<?> changeNickName(@RequestHeader(value = "Authorization") String headerToken, @RequestBody String nickname){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        UserEntity newUser = userService.changeNickName(userId, nickname);
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }

    @PutMapping("/api/users/profile-image")
    public ResponseEntity<?> changeProfileImage(@RequestHeader(value = "Authorization")String headerToken, @RequestBody String profileImage){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = tokenService.findUserIdByJwt(token);
        UserEntity newUser = userService.changeProfileImage(userId, profileImage);
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }
    @GetMapping("/api/users/following")
    public ResponseEntity<?> loadFollowingList(@RequestHeader(value = "Authorization") String headerToken){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        List<FollowDto> followings = userService.findFollowingList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(followings);
    }

    @GetMapping("/api/users/follower")
    public ResponseEntity<?> loadFollowerList(@RequestHeader(value = "Authorization") String headerToken){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        List<FollowDto> followers = userService.findFollowerList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(followers);
    }

    @GetMapping("/api/users/likes/diaries")
    public ResponseEntity<?> loadLikeDiaryList(@RequestHeader(value = "Authorization") String headerToken){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        List<DiaryDto> diaryDtos = userService.findLikeDiaryList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryDtos);
    }

    @GetMapping("/api/users/likes/guestbooks")
    public ResponseEntity<?> loadLikeGuestBookList(@RequestHeader(value = "Authorization") String headerToken){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = tokenService.findUserIdByJwt(token);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        List<GuestBookDto> guestBookDtos = userService.findLikeGuestBookList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(guestBookDtos);
    }
}

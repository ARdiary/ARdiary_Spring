package com.army.ardiary.controller;

import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.DiaryDto;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.FollowDto;
import com.army.ardiary.dto.GuestBookDto;
import com.army.ardiary.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/{nickname}")
public class UserController {

    private final TokenService tokenService;
    private final UserService userService;
    private final DiaryService diaryService;
    private final TimeCapsuleService timeCapsuleService;
    private final GuestBookService guestBookService;

    @GetMapping
    public ResponseEntity<?> getUserInfo(@RequestHeader(value = "Authorization") String headerToken, @PathVariable String nickname ){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));
        int userId = userService.findUserByNickName(nickname);
        UserEntity userEntity = userService.findUserInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userEntity);
    }

    @GetMapping("/userChk")
    public ResponseEntity<?> checkUserByNickName(@RequestParam String nickname){
        int result = userService.findUserByNickName(nickname);
        if (result==0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 nickname을 가진 user가 존재하지 않습니다. (닉네임 사용 가능)");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body("해당 nickname을 가진 user의 id는 "+result+"입니다. (닉네임 사용 불가)");
        }
    }

    @PutMapping("/nickname")
    public ResponseEntity<?> changeNickName(@RequestHeader(value = "Authorization") String headerToken,
                                            @PathVariable String nickname,
                                            @RequestBody String newNickname){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userIdByJwt = tokenService.findUserIdByJwt(token);
        int userId = userService.findUserByNickName(nickname);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));
        else if (userIdByJwt!=userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("수정 권한이 없습니다"));
        }

        UserEntity newUser = userService.modifyNickName(userId, nickname);
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }

    @PutMapping("/profile-image")
    public ResponseEntity<?> changeProfileImage(@RequestHeader(value = "Authorization")String headerToken,
                                                @PathVariable String nickname,
                                                @RequestBody MultipartFile[] newProfileImage){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }

        int userIdByJwt = tokenService.findUserIdByJwt(token);
        int userId = userService.findUserByNickName(nickname);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));
        else if (userIdByJwt!=userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("수정 권한이 없습니다"));
        }
        UserEntity newUser = userService.modifyProfileImage(userId, newProfileImage);
        return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }
    @GetMapping("/following")
    public ResponseEntity<?> loadFollowingList(@RequestHeader(value = "Authorization") String headerToken, @PathVariable String nickname){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        int userId = userService.findUserByNickName(nickname);
        List<FollowDto> followings = userService.findFollowingList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(followings);
    }

    @GetMapping("/follower")
    public ResponseEntity<?> loadFollowerList(@RequestHeader(value = "Authorization") String headerToken,@PathVariable String nickname){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        int userId = userService.findUserByNickName(nickname);
        List<FollowDto> followers = userService.findFollowerList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(followers);
    }

    @GetMapping("/likes/diaries")
    public ResponseEntity<?> loadLikeDiaryList(@RequestHeader(value = "Authorization") String headerToken,@PathVariable String nickname){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }

        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        int userId = userService.findUserByNickName(nickname);
        List<DiaryDto> diaryDtos = userService.findLikeDiaryList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryDtos);
    }

    @GetMapping("/likes/guestbooks")
    public ResponseEntity<?> loadLikeGuestBookList(@RequestHeader(value = "Authorization") String headerToken,@PathVariable String nickname){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        int userId = userService.findUserByNickName(nickname);
        if(token == null || !tokenService.validateToken(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패"));

        List<GuestBookDto> guestBookDtos = userService.findLikeGuestBookList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(guestBookDtos);
    }

    @GetMapping("/diaries")
    public ResponseEntity<?> loadDiaryList(@RequestHeader(value = "Authorization") String headerToken, @PathVariable String nickname){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        //토큰 유효성 확인
        if(token == null || !tokenService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("사용자 인증이 불가합니다. 잘못된 접근입니다."));
        }

        int userId = userService.findUserByNickName(nickname);
        //다이어리 불러오기
        List<DiaryDto> diaryListDto= diaryService.findByWriter(userId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryListDto);
    }

    @GetMapping("/timecapsules")
    public ResponseEntity<?> loadTimecapsuleList(@RequestHeader(value = "Authorization") String headerToken, @PathVariable String nickname){
            String token=headerToken;
            if(token.substring(0,7).equals("Bearer ")) {
                token = headerToken.substring("Bearer ".length());
            }
            int userId = userService.findUserByNickName(nickname);
            if (token == null || !tokenService.validateToken(token))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("토큰 인증 실패. 조회 권한이 없습니다."));
            List<GuestBookDto> guestBookDtoList = guestBookService.findGuestBookListByUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(guestBookDtoList);
        }

    @GetMapping("/guestbooks")
    public ResponseEntity<?> loadGuestBookList(@RequestHeader(value = "Authorization") String headerToken, @PathVariable String nickname){
        String token=headerToken;
        if(token.substring(0,7).equals("Bearer ")) {
            token = headerToken.substring("Bearer ".length());
        }
        //토큰 유효성 확인
        if(token == null || !tokenService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("사용자 인증이 불가합니다. 잘못된 접근입니다."));
        }
        int userId = userService.findUserByNickName(nickname);
        //다이어리 불러오기
        List<DiaryDto> diaryListDto= diaryService.findByWriter(userId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryListDto);
    }
}

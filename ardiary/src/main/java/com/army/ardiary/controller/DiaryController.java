package com.army.ardiary.controller;

import com.army.ardiary.domain.entity.DiaryEntity;
import com.army.ardiary.dto.DiaryDto;
import com.army.ardiary.dto.DiaryRequestDto;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.service.DiaryService;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
public class DiaryController {
    //다이어리 목록을 불러오는 api를 다루는 컨트롤러

    private final DiaryService diaryService;
    private final TokenService tokenService;

    @GetMapping()
    public ResponseEntity<?> loadDiary(){
        List<DiaryDto> diaryListDto=diaryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(diaryListDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> loadDiaryById(@PathVariable(value = "id") int diaryId){
        DiaryDto diaryDto = diaryService.findById(diaryId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryDto);
    }

    //이거 유저로 빠질 예정
    @GetMapping("api/user/diary")
    public ResponseEntity<?> loadDiariesByUser(@RequestHeader(value = "Authorization") String headerToken){
        String token = headerToken.substring("Bearer ".length());
        //토큰 유효성 확인
        if(!tokenService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("사용자 인증이 불가합니다. 잘못된 접근입니다."));
        }
        //토큰을 통해 사용자 정보 불러오기
        int userId = tokenService.findUserIdByJwt(token);
        //다이어리 불러오기
        List<DiaryDto> diaryListDto= diaryService.findByWriter(userId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryListDto);
    }

    @GetMapping("/marker")
    public ResponseEntity<?> loadDiaryByMarker(@RequestParam("id") int markerId){
        DiaryDto diaryDto = diaryService.findByMarker(markerId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryDto);
    }

    @PostMapping("")
    public ResponseEntity<?> writeDiary(@RequestHeader(value = "Authorization") String headerToken, DiaryRequestDto diaryRequestDto){
        String token = headerToken.substring("Bearer ".length());
        //토큰 유효성 확인
        if(token==null){ //토큰을 보내지않은 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 요청입니다. 토큰도 함께 보내주세요."));
        }
        if(!tokenService.validateToken(token)){ //토큰이 유효하지않은 경우: 지금 이 에러 발생. 토큰이 변조된 상황.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("사용자 인증이 불가합니다. 잘못된 접근입니다."));
        }
        //토큰을 통해 사용자 정보 불러오기
        int userId = tokenService.findUserIdByJwt(token);
        //일기 생성 후, 작성된 일기 불러오기
        int newDiaryId=diaryService.createDiary(diaryRequestDto,userId);
        DiaryDto diaryDto =diaryService.findById(newDiaryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(diaryDto);
    }

    //@RequestMapping(value = "/diary/delete/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiary(@PathVariable(value = "id") int diaryId, @RequestHeader(value = "Authorization") String headerToken){
        String token = headerToken.substring("Bearer ".length());
        //토큰 유효성 확인
        if(token==null||!tokenService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("사용자 인증이 불가합니다. 잘못된 접근입니다."));
        }
        //토큰을 통해 사용자 정보 불러오기
        int userId = tokenService.findUserIdByJwt(token);
        int deleteCount = diaryService.deleteDiary(diaryId,userId);
        if(deleteCount == 0){
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ErrorResponse("삭제가 실패했습니다"));
        }
        return ResponseEntity.ok("삭제 완료");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> modifyDiary(@PathVariable(value = "id") int diaryId,@RequestHeader(value = "Authorization") String headerToken, @RequestBody @Valid DiaryRequestDto diaryRequestDto){
        String token = headerToken.substring("Bearer ".length());
        //토큰 유효성 확인
        if(!tokenService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("사용자 인증이 불가합니다. 잘못된 접근입니다."));
        }
        //토큰을 통해 사용자 정보 불러오기
        int userId = tokenService.findUserIdByJwt(token);
        int updateDiaryId=diaryService.modifyDiary(diaryId, diaryRequestDto,userId);
        DiaryDto diaryDto = diaryService.findById(updateDiaryId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryDto);
    }

/*    @PostMapping("/user/diary/like")
    public ResponseEntity<?> listDiaryByUserLike(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        String token= tokenRequestDto.getJwt();
        //validateToken함수 오류 확인후 후 주석 지우기
        if(!tokenService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("사용자 인증이 불가합니다. 잘못된 접근입니다."));
        }
        DiaryResponseDto diaryResponseDto=diaryListService.listMine(token);
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseDto);
    }*/


}

package com.army.ardiary.controller;

import com.army.ardiary.dto.DiaryResponseDto;
import com.army.ardiary.dto.ErrorResponse;
import com.army.ardiary.dto.IdRequestDto;
import com.army.ardiary.dto.TokenRequestDto;
import com.army.ardiary.service.DiaryListService;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class DiaryListController {
    //다이어리 목록을 불러오는 api를 다루는 컨트롤러

    //
    private final DiaryListService diaryListService;
    private TokenService tokenService;

    @PostMapping("/diary")
    public ResponseEntity<?> listDiary(){
        DiaryResponseDto diaryResponseDto=diaryListService.listAll();
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseDto);
    }

    @GetMapping("/diary/{id}")
    public ResponseEntity<?> listDiaryById(@PathVariable(value = "id") int diaryId){
        DiaryResponseDto diaryResponseDto =diaryListService.listById(diaryId);
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseDto);
    }
    @PostMapping("/user/diary")
    public ResponseEntity<?> listDiaryByUser(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        String token= tokenRequestDto.getJwt();
        if(!tokenService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("사용자 인증이 불가합니다. 잘못된 접근입니다."));
        }
        DiaryResponseDto diaryResponseDto=diaryListService.listMine(token);
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseDto);
    }

    @PostMapping("/user/diary/like")
    public ResponseEntity<?> listDiaryByUserLike(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        String token= tokenRequestDto.getJwt();
        //validateToken함수 오류 확인후 후 주석 지우기
//        if(!tokenService.validateToken(token)){
 //           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("사용자 인증이 불가합니다. 잘못된 접근입니다."));
   //     }
        DiaryResponseDto diaryResponseDto=diaryListService.listMine(token);
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseDto);
    }


}

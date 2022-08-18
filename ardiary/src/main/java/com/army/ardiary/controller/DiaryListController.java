package com.army.ardiary.controller;

import com.army.ardiary.dto.DiaryResponseDto;
import com.army.ardiary.dto.TokenRequestDto;
import com.army.ardiary.service.DiaryListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequiredArgsConstructor
public class DiaryListController {
    //다이어리 목록을 불러오는 api를 다루는 컨트롤러

    //
    private DiaryListService diaryListService;

    @PostMapping("/user/diary")
    public ResponseEntity<?> listDiary(@RequestBody @Valid TokenRequestDto tokenRequestDto){
        String token= tokenRequestDto.getJwt();
        DiaryResponseDto diaryResponseDto=diaryListService.listMine(token);
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseDto);
    }


}

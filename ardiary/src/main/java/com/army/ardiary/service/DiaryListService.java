package com.army.ardiary.service;

import com.army.ardiary.domain.entity.DiaryEntity;
import com.army.ardiary.dto.DiaryResponseDto;
import com.army.ardiary.repository.DiaryRepository;
import io.jsonwebtoken.Claims;
import com.army.ardiary.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DiaryListService {

    private DiaryRepository diaryRepository;
    private TokenService tokenService;

    public DiaryResponseDto listMine(String token){
        Claims claims= tokenService.getJwtContents(token);
        int id= Integer.parseInt(claims.getSubject());
        ArrayList<DiaryEntity> diaryEntities=diaryRepository.selectByWriter(id);
        DiaryResponseDto diaryResponseDto= DiaryResponseDto.builder()
                .diaryList(diaryEntities)
                .build();

        return diaryResponseDto;
    }



}

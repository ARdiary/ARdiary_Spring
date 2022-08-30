package com.army.ardiary.service;

import com.army.ardiary.domain.entity.DiaryEntity;
import com.army.ardiary.dto.DiaryResponseDto;
import com.army.ardiary.repository.DiaryRepository;
import io.jsonwebtoken.Claims;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DiaryListService {

    private final DiaryRepository diaryRepository;
    private final TokenService tokenService;

    public DiaryResponseDto listMine(String token){
        Claims claims= tokenService.getJwtContents(token);
        int userId= Integer.parseInt(claims.getSubject());
        ArrayList<DiaryEntity> diaryEntities =diaryRepository.selectByWriter(userId);
        DiaryResponseDto diaryResponseDto= DiaryResponseDto.builder()
                .diaryList(diaryEntities)
                .build();
        return diaryResponseDto;
    }

    public DiaryResponseDto listAll(){
        ArrayList<DiaryEntity> diaryEntities =diaryRepository.selectAll();
        DiaryResponseDto diaryResponseDto= DiaryResponseDto.builder()
                .diaryList(diaryEntities)
                .build();
        return diaryResponseDto;
    }

public DiaryResponseDto listById(int id){
    ArrayList<DiaryEntity> diaryEntities = new ArrayList<>();
    DiaryEntity diaryEntity=diaryRepository.selectById(id);
    diaryEntities.add(diaryEntity);
    DiaryResponseDto diaryResponseDto= DiaryResponseDto.builder()
            .diaryList(diaryEntities)
            .build();
    return diaryResponseDto;
}

    public DiaryResponseDto listLike(String token){
        Claims claims= tokenService.getJwtContents(token);
        int userId= Integer.parseInt(claims.getSubject());
        DiaryEntity diaryEntity=diaryRepository.selectById(userId);
        DiaryResponseDto diaryResponseDto= DiaryResponseDto.builder()
                //.diaryList(diaryEntities)
                .build();
        return diaryResponseDto;
    }

}

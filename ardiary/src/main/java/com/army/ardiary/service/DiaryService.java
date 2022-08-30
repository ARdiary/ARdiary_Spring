package com.army.ardiary.service;

import com.army.ardiary.domain.entity.DiaryEntity;
import com.army.ardiary.dto.DiaryResponseDto;
import com.army.ardiary.repository.DiaryRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final TokenService tokenService;

    public DiaryResponseDto findByWriter(int userId){
        ArrayList<DiaryEntity> diaryEntities =diaryRepository.selectByWriter(userId);
        DiaryResponseDto diaryResponseDto= DiaryResponseDto.builder()
                .diaryList(diaryEntities)
                .build();
        return diaryResponseDto;
    }

    public DiaryResponseDto findAll(){
        ArrayList<DiaryEntity> diaryEntities =diaryRepository.selectAll();
        DiaryResponseDto diaryResponseDto= DiaryResponseDto.builder()
                .diaryList(diaryEntities)
                .build();
        return diaryResponseDto;
    }

public DiaryResponseDto findById(int id){
    ArrayList<DiaryEntity> diaryEntities = new ArrayList<>();
    DiaryEntity diaryEntity=diaryRepository.selectById(id);
    diaryEntities.add(diaryEntity);
    DiaryResponseDto diaryResponseDto= DiaryResponseDto.builder()
            .diaryList(diaryEntities)
            .build();
    return diaryResponseDto;
}

//위 함수 오버로딩(찜한 일기목록을 불러올때 사용)
    public DiaryResponseDto findById(int[] id){

        DiaryResponseDto diaryResponseDto= DiaryResponseDto.builder()
                //.diaryList(diaryEntities)
                .build();
        return diaryResponseDto;
    }

}

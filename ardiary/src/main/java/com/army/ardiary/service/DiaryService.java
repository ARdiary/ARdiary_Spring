package com.army.ardiary.service;

import com.army.ardiary.domain.entity.DiaryEntity;
import com.army.ardiary.dto.DiaryRequestDto;
import com.army.ardiary.dto.DiaryResponseDto;
import com.army.ardiary.repository.DiaryRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final TokenService tokenService;
    private final FileService fileService;

    public DiaryResponseDto findByWriter(int userId){
        ArrayList<DiaryEntity> diaryEntities =diaryRepository.selectByWriter(userId);
        for(DiaryEntity diaryEntity: diaryEntities){
        }
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
    public DiaryResponseDto findById(List<Integer> id){
        DiaryResponseDto diaryResponseDto= DiaryResponseDto.builder()
                //.diaryList(diaryEntities)
                .build();
        return diaryResponseDto;
    }

    public int createDiary(DiaryRequestDto diaryRequestDto, int writer){
        String imagePath=fileService.uploadFiles(diaryRequestDto.getImages());
        String videoPath=fileService.uploadFiles(diaryRequestDto.getVideos());
        String audioPath=fileService.uploadFiles(diaryRequestDto.getAudios());

        DiaryEntity newDiaryEntity= DiaryEntity.builder()
                .writer(writer).title(diaryRequestDto.getTitle()).content(diaryRequestDto.getContent())
                .privacyOption(diaryRequestDto.getPrivacyOption())
                .image(imagePath).video(videoPath).audio(audioPath)
                .build();
        diaryRepository.insert(newDiaryEntity);
        int newDiaryID= newDiaryEntity.getDiaryId();
        return newDiaryID;
    }

    public int deleteDiary(int diaryId, int writer){
        DiaryEntity diaryEntity=diaryRepository.selectById(diaryId);
        if(diaryEntity.getWriter()==writer){
            int isSuccess=diaryRepository.delete(diaryId);
            if(isSuccess==1){
                return 1;
            }
        }
        return 0;
    }
}

package com.army.ardiary.service;

import com.army.ardiary.domain.entity.DiaryEntity;
import com.army.ardiary.dto.DiaryRequestDto;
import com.army.ardiary.dto.DiaryResponseDto;
import com.army.ardiary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        String imagePath=fileService.uploadFiles(diaryRequestDto.getImages(),"diary","image");
        String videoPath=fileService.uploadFiles(diaryRequestDto.getVideos(),"diary","video");
        String audioPath=fileService.uploadFiles(diaryRequestDto.getAudios(),"diary","audio");

        DiaryEntity newDiaryEntity= DiaryEntity.builder()
                .writer(writer).title(diaryRequestDto.getTitle()).content(diaryRequestDto.getContent())
                .privacyOption(diaryRequestDto.getPrivacyOption())
                .image(imagePath).video(videoPath).audio(audioPath).cameraARId(diaryRequestDto.getCameraARId())
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
    public int modifyDiary(int diaryId, DiaryRequestDto diaryRequestDto, int writer){
        DiaryEntity oldDiaryEntity=diaryRepository.selectById(diaryId);
        DiaryEntity updateDiaryEntity=oldDiaryEntity;

        if(oldDiaryEntity.getWriter()!=writer){
            return 0;
        }
        
        //patch작업을 위한 조건물 및 updateDiaryEntity초기화문
        if(diaryRequestDto.getTitle()!=null&&diaryRequestDto.getTitle().length()>0)
            updateDiaryEntity.setTitle(diaryRequestDto.getTitle());
        if(diaryRequestDto.getContent()!=null&&diaryRequestDto.getContent().length()>0)
            updateDiaryEntity.setContent(diaryRequestDto.getContent());
        if(diaryRequestDto.getImages()!=null&&diaryRequestDto.getImages().length>0){
            String imagePath=fileService.uploadFiles(diaryRequestDto.getImages(),"diary","image");
            updateDiaryEntity.setImage(imagePath);
        }
        if(diaryRequestDto.getVideos()!=null&&diaryRequestDto.getVideos().length>0){
            String videoPath=fileService.uploadFiles(diaryRequestDto.getVideos(),"diary","video");
            updateDiaryEntity.setVideo(videoPath);
        }
        if(diaryRequestDto.getImages()!=null&&diaryRequestDto.getImages().length>0){
            String audioPath=fileService.uploadFiles(diaryRequestDto.getAudios(),"diary","audio");
            updateDiaryEntity.setVideo(audioPath);
        }

        try {
            if(1<=diaryRequestDto.getPrivacyOption()&&diaryRequestDto.getPrivacyOption()<=3){
                updateDiaryEntity.setPrivacyOption(diaryRequestDto.getPrivacyOption());
            }
        } catch (Exception e) {
        }
        
        diaryRepository.update(updateDiaryEntity);
        int updateDiaryId=updateDiaryEntity.getDiaryId();
        return updateDiaryId;
    }
}

package com.army.ardiary.service;

import com.army.ardiary.domain.entity.DiaryEntity;
import com.army.ardiary.dto.DiaryDto;
import com.army.ardiary.dto.DiaryRequestDto;
import com.army.ardiary.repository.DiaryRepository;
import com.army.ardiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Marker;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final TokenService tokenService;
    private final FileService fileService;
    private final MarkerService markerService;
    private final UserRepository userRepository;

    public List<DiaryDto> findByWriter(int userId){
        ArrayList<DiaryEntity> diaryEntities =diaryRepository.selectByWriter(userId);
        List<DiaryDto> diaryList= new ArrayList<>();
        for(DiaryEntity diaryEntity: diaryEntities){
            DiaryDto diaryDto = DiaryDto.builder()
                    .diaryId(diaryEntity.getDiaryId()).date(diaryEntity.getDate())
                    .ARMarkerId(diaryEntity.getARMarkerId())
                    .audio(diaryEntity.getAudio()).image(diaryEntity.getImage()).video(diaryEntity.getVideo())
                    .privacyOption(diaryEntity.getPrivacyOption())
                    .content(diaryEntity.getContent())
                    .writer(userRepository.selectById(userId).getNickname())
                    .build();
            diaryList.add(diaryDto);
        }
        return diaryList;
    }

    public List<DiaryDto> findAll(){
        ArrayList<DiaryEntity> diaryEntities =diaryRepository.selectAll();
        List<DiaryDto> diaryList= new ArrayList<>();
        for(DiaryEntity diaryEntity: diaryEntities){
            DiaryDto diaryDto = DiaryDto.builder()
                    .diaryId(diaryEntity.getDiaryId()).date(diaryEntity.getDate())
                    .ARMarkerId(diaryEntity.getARMarkerId())
                    .audio(diaryEntity.getAudio()).image(diaryEntity.getImage()).video(diaryEntity.getVideo())
                    .privacyOption(diaryEntity.getPrivacyOption())
                    .content(diaryEntity.getContent())
                    .writer(userRepository.selectById(diaryEntity.getWriter()).getNickname())
                    .build();
            diaryList.add(diaryDto);
        }
        return diaryList;
    }

public DiaryDto findById(int id){
    DiaryEntity diaryEntity=diaryRepository.selectById(id);
    DiaryDto diaryDto = DiaryDto.builder()
            .diaryId(diaryEntity.getDiaryId()).date(diaryEntity.getDate())
            .ARMarkerId(diaryEntity.getARMarkerId())
            .audio(diaryEntity.getAudio()).image(diaryEntity.getImage()).video(diaryEntity.getVideo())
            .privacyOption(diaryEntity.getPrivacyOption())
            .content(diaryEntity.getContent())
            .writer(userRepository.selectById(diaryEntity.getWriter()).getNickname())
            .build();
    return diaryDto;
}

    public DiaryDto findByMarker(int id){
        DiaryEntity diaryEntity = diaryRepository.selectByARMarkerId(id);
        DiaryDto diaryDto = DiaryDto.builder()
                .diaryId(diaryEntity.getDiaryId()).date(diaryEntity.getDate())
                .ARMarkerId(diaryEntity.getARMarkerId())
                .audio(diaryEntity.getAudio()).image(diaryEntity.getImage()).video(diaryEntity.getVideo())
                .privacyOption(diaryEntity.getPrivacyOption())
                .content(diaryEntity.getContent())
                .writer(userRepository.selectById(diaryEntity.getWriter()).getNickname())
                .build();
        return diaryDto;
    }

//위 함수 오버로딩(찜한 일기목록을 불러올때 사용)
    public List<DiaryDto> findById(List<Integer> idList){
        List<DiaryDto> diaryList= new ArrayList<>();
        for(int id:idList){
            DiaryEntity diaryEntity=diaryRepository.selectById(id);
            DiaryDto diaryDto = DiaryDto.builder()
                    .diaryId(diaryEntity.getDiaryId()).date(diaryEntity.getDate())
                    .ARMarkerId(diaryEntity.getARMarkerId())
                    .audio(diaryEntity.getAudio()).image(diaryEntity.getImage()).video(diaryEntity.getVideo())
                    .privacyOption(diaryEntity.getPrivacyOption())
                    .content(diaryEntity.getContent())
                    .writer(userRepository.selectById(diaryEntity.getWriter()).getNickname())
                    .build();
            diaryList.add(diaryDto);
        }
        return diaryList;
    }

    public int createDiary(DiaryRequestDto diaryRequestDto, int writer){

        String imagePath=fileService.uploadFiles(diaryRequestDto.getImages(),"diary","image");
        String videoPath=fileService.uploadFiles(diaryRequestDto.getVideos(),"diary","video");
        String audioPath=fileService.uploadFiles(diaryRequestDto.getAudios(),"diary","audio");

        DiaryEntity newDiaryEntity= DiaryEntity.builder()
                .writer(writer).title(diaryRequestDto.getTitle()).content(diaryRequestDto.getContent())
                .privacyOption(diaryRequestDto.getPrivacyOption())
                .image(imagePath).video(videoPath).audio(audioPath).ARMarkerId(diaryRequestDto.getARMarkerId())
                .build();
        diaryRepository.insert(newDiaryEntity);
        int newDiaryID= newDiaryEntity.getDiaryId();
        return newDiaryID;
    }

    public int deleteDiary(int diaryId, int writer){
        DiaryEntity diaryEntity=diaryRepository.selectById(diaryId);
        if(diaryEntity.getWriter()==writer){
            int isSuccess=diaryRepository.delete(diaryId);
            if(isSuccess==1&&markerService.delete(diaryEntity.getARMarkerId())==1){
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
        updateDiaryEntity.setARMarkerId(diaryRequestDto.getARMarkerId());
        diaryRepository.update(updateDiaryEntity);
        int updateDiaryId=updateDiaryEntity.getDiaryId();
        return updateDiaryId;
    }
}

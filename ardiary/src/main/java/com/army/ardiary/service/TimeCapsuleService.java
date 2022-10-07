package com.army.ardiary.service;

import com.army.ardiary.domain.entity.TimeCapsuleEntity;
import com.army.ardiary.dto.TimeCapsuleDto;
import com.army.ardiary.repository.TimeCapsuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeCapsuleService {

    private final TimeCapsuleRepository timeCapsuleRepository;

    public TimeCapsuleEntity createTimeCapsule(int userId, TimeCapsuleDto timeCapsuleDto){

        TimeCapsuleEntity timeCapsuleEntity = TimeCapsuleEntity.builder()
                .writer(userId)
                .title(timeCapsuleDto.getTitle())
                .content(timeCapsuleDto.getContent())
                .image(timeCapsuleDto.getImage())
                .video(timeCapsuleDto.getVideo())
                .audio(timeCapsuleDto.getAudio())
                .build();

        timeCapsuleRepository.insert(timeCapsuleEntity);
        TimeCapsuleEntity newTimeCapsule = timeCapsuleRepository.selectById(userId);
        return newTimeCapsule;
    }
}

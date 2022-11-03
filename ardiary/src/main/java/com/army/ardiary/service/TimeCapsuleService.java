package com.army.ardiary.service;

import com.army.ardiary.domain.entity.ParticipantEntity;
import com.army.ardiary.domain.entity.TimeCapsuleEntity;
import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.TimeCapsuleDto;
import com.army.ardiary.dto.TimeCapsuleRequestDto;
import com.army.ardiary.repository.ParticipantRepository;
import com.army.ardiary.repository.TimeCapsuleRepository;
import com.army.ardiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeCapsuleService {

    private final TimeCapsuleRepository timeCapsuleRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;

    public TimeCapsuleRequestDto createTimeCapsule(int userId, TimeCapsuleDto timeCapsuleDto){

        TimeCapsuleEntity timeCapsuleEntity = TimeCapsuleEntity.builder()
                .writer(userId)
                .title(timeCapsuleDto.getTitle())
                .content(timeCapsuleDto.getContent())
                .image(timeCapsuleDto.getImage())
                .video(timeCapsuleDto.getVideo())
                .audio(timeCapsuleDto.getAudio())
                .build();

        timeCapsuleRepository.insert(timeCapsuleEntity);
        TimeCapsuleEntity newTimeCapsule = timeCapsuleRepository.selectById(timeCapsuleEntity.getTimeCapsuleId());

        ArrayList<ParticipantEntity> participantEntities = new ArrayList<>();
        for(String email: timeCapsuleDto.getParticipants()){
            UserEntity userEntity = userRepository.selectByEmail(email);
            ParticipantEntity participantEntity = ParticipantEntity.builder()
                    .timeCapsuleId(timeCapsuleEntity.getTimeCapsuleId())
                    .userId(userEntity.getUserId())
                    .build();
            participantRepository.insert(participantEntity);
            participantEntities.add(participantEntity);
        }

        TimeCapsuleRequestDto timeCapsuleRequestDto = TimeCapsuleRequestDto.builder()
                .timeCapsule(newTimeCapsule)
                .participants(participantEntities)
                .build();
        return timeCapsuleRequestDto;
    }

    public TimeCapsuleRequestDto findTimeCapsule(int timeCapsuleId){
        TimeCapsuleEntity timeCapsuleEntity = timeCapsuleRepository.selectById(timeCapsuleId);
        ArrayList<ParticipantEntity> participantEntities = participantRepository.selectByTimeCapsuleId(timeCapsuleId);
        TimeCapsuleRequestDto timeCapsuleRequestDto = TimeCapsuleRequestDto.builder()
                .timeCapsule(timeCapsuleEntity)
                .participants(participantEntities)
                .build();
        return timeCapsuleRequestDto;
    }

    public TimeCapsuleRequestDto delete(int timeCapsuleId){
        TimeCapsuleEntity timeCapsuleEntity = timeCapsuleRepository.selectById(timeCapsuleId);
        ArrayList<ParticipantEntity> participantEntities = participantRepository.selectByTimeCapsuleId(timeCapsuleId);
        TimeCapsuleRequestDto timeCapsuleRequestDto = TimeCapsuleRequestDto.builder()
                .timeCapsule(timeCapsuleEntity)
                .participants(participantEntities)
                .build();

        timeCapsuleRepository.delete(timeCapsuleId);
        for(ParticipantEntity participantEntity: participantEntities){
            int id = participantEntity.getParticipantId();
            participantRepository.delete(id);
        }

        return timeCapsuleRequestDto;
    }
}


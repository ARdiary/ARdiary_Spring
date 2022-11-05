package com.army.ardiary.service;

import com.army.ardiary.domain.entity.ParticipantEntity;
import com.army.ardiary.domain.entity.TimeCapsuleEntity;
import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.TimeCapsuleRequestDto;
import com.army.ardiary.dto.TimeCapsuleResponseDto;
import com.army.ardiary.repository.ParticipantRepository;
import com.army.ardiary.repository.TimeCapsuleRepository;
import com.army.ardiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TimeCapsuleService {

    private final TimeCapsuleRepository timeCapsuleRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;

    public TimeCapsuleResponseDto createTimeCapsule(int userId, TimeCapsuleRequestDto timeCapsuleRequestDto){

        TimeCapsuleEntity timeCapsuleEntity = TimeCapsuleEntity.builder()
                .writer(userId)
                .title(timeCapsuleRequestDto.getTitle())
                .content(timeCapsuleRequestDto.getContent())
                .image(timeCapsuleRequestDto.getImage())
                .video(timeCapsuleRequestDto.getVideo())
                .audio(timeCapsuleRequestDto.getAudio())
                .build();

        timeCapsuleRepository.insert(timeCapsuleEntity);
        TimeCapsuleEntity newTimeCapsule = timeCapsuleRepository.selectById(timeCapsuleEntity.getTimeCapsuleId());

        ArrayList<ParticipantEntity> participantEntities = new ArrayList<>();
        for(String email: timeCapsuleRequestDto.getParticipants()){
            UserEntity userEntity = userRepository.selectByEmail(email);
            ParticipantEntity participantEntity = ParticipantEntity.builder()
                    .timeCapsuleId(timeCapsuleEntity.getTimeCapsuleId())
                    .userId(userEntity.getUserId())
                    .build();
            participantRepository.insert(participantEntity);
            participantEntities.add(participantEntity);
        }

        TimeCapsuleResponseDto timeCapsuleResponseDto = TimeCapsuleResponseDto.builder()
                .timeCapsule(newTimeCapsule)
                .participants(participantEntities)
                .build();
        return timeCapsuleResponseDto;
    }

    public TimeCapsuleResponseDto findTimeCapsule(int timeCapsuleId){
        TimeCapsuleEntity timeCapsuleEntity = timeCapsuleRepository.selectById(timeCapsuleId);
        ArrayList<ParticipantEntity> participantEntities = participantRepository.selectByTimeCapsuleId(timeCapsuleId);
        TimeCapsuleResponseDto timeCapsuleResponseDto = TimeCapsuleResponseDto.builder()
                .timeCapsule(timeCapsuleEntity)
                .participants(participantEntities)
                .build();
        return timeCapsuleResponseDto;
    }

    public TimeCapsuleResponseDto delete(int timeCapsuleId){
        TimeCapsuleEntity timeCapsuleEntity = timeCapsuleRepository.selectById(timeCapsuleId);
        ArrayList<ParticipantEntity> participantEntities = participantRepository.selectByTimeCapsuleId(timeCapsuleId);
        TimeCapsuleResponseDto timeCapsuleResponseDto = TimeCapsuleResponseDto.builder()
                .timeCapsule(timeCapsuleEntity)
                .participants(participantEntities)
                .build();

        timeCapsuleRepository.delete(timeCapsuleId);
        for(ParticipantEntity participantEntity: participantEntities){
            int id = participantEntity.getParticipantId();
            participantRepository.delete(id);
        }

        return timeCapsuleResponseDto;
    }
}


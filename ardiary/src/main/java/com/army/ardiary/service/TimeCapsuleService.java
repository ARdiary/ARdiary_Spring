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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeCapsuleService {

    private final TimeCapsuleRepository timeCapsuleRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final FileService fileService;

    public int createTimeCapsule(int userId, TimeCapsuleRequestDto timeCapsuleRequestDto){

        String imagePath=fileService.uploadFiles(timeCapsuleRequestDto.getImage(),"timecapsule","image");
        String videoPath=fileService.uploadFiles(timeCapsuleRequestDto.getVideo(),"timecapsule","video");
        String audioPath=fileService.uploadFiles(timeCapsuleRequestDto.getAudio(),"timecapsule","audio");

        TimeCapsuleEntity timeCapsuleEntity = TimeCapsuleEntity.builder()
                .writer(userId)
                .title(timeCapsuleRequestDto.getTitle())
                .content(timeCapsuleRequestDto.getContent())
                .dueDate(timeCapsuleRequestDto.getDueDate())
                .image(imagePath)
                .video(videoPath)
                .audio(audioPath)
                .ARMarkerId(timeCapsuleRequestDto.getARMarkerId())
                .build();

        timeCapsuleRepository.insert(timeCapsuleEntity);

        TimeCapsuleEntity newTimeCapsule = timeCapsuleRepository.selectById(timeCapsuleEntity.getTimeCapsuleId());

        List<ParticipantEntity> participantEntities = new ArrayList<>();
        for(String nickname: timeCapsuleRequestDto.getParticipants()){
            UserEntity userEntity = userRepository.selectByNickname(nickname);
            ParticipantEntity participantEntity = ParticipantEntity.builder()
                    .timeCapsuleId(timeCapsuleEntity.getTimeCapsuleId())
                    .userId(userEntity.getUserId())
                    .build();
            participantRepository.insert(participantEntity);
            participantEntities.add(participantEntity);
        }

        int newTimeCapsuleId = newTimeCapsule.getTimeCapsuleId();

        return newTimeCapsuleId;
    }

    public TimeCapsuleResponseDto findTimeCapsule(int timeCapsuleId){
        TimeCapsuleEntity timeCapsuleEntity = timeCapsuleRepository.selectById(timeCapsuleId);
        List<ParticipantEntity> participantEntities = participantRepository.selectByTimeCapsuleId(timeCapsuleId);

        UserEntity writer = userRepository.selectById(timeCapsuleEntity.getWriter());
        String nickname = writer.getNickname();


        List<String> participants = new ArrayList<>();
        for(ParticipantEntity participantEntity: participantEntities){
            int userId = participantEntity.getUserId();
            UserEntity userEntity = userRepository.selectById(userId);
            String participantNickname = userEntity.getNickname();
            participants.add(participantNickname);
        }

        TimeCapsuleResponseDto timeCapsuleResponseDto = TimeCapsuleResponseDto.builder()
                .timeCapsuleId(timeCapsuleEntity.getTimeCapsuleId())
                .writer(nickname)
                .title(timeCapsuleEntity.getTitle())
                .content(timeCapsuleEntity.getContent())
                .date(timeCapsuleEntity.getDate())
                .dueDate(timeCapsuleEntity.getDueDate())
                .participants(participants)
                .image(timeCapsuleEntity.getImage())
                .video(timeCapsuleEntity.getVideo())
                .audio(timeCapsuleEntity.getAudio())
                .ARMarkerId(timeCapsuleEntity.getARMarkerId())
                .build();
        return timeCapsuleResponseDto;
    }

    public void delete(int timeCapsuleId){
        List<ParticipantEntity> participantEntities = participantRepository.selectByTimeCapsuleId(timeCapsuleId);

        timeCapsuleRepository.delete(timeCapsuleId);
        for(ParticipantEntity participantEntity: participantEntities){
            int id = participantEntity.getParticipantId();
            participantRepository.delete(id);
        }
    }
}

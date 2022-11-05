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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeCapsuleService {

    private final TimeCapsuleRepository timeCapsuleRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final FileService fileService;

    public int createTimeCapsule(int userId, TimeCapsuleRequestDto timeCapsuleRequestDto)throws ParseException {

        String imagePath=fileService.uploadFiles(timeCapsuleRequestDto.getImage(),"timecapsule","image");
        String videoPath=fileService.uploadFiles(timeCapsuleRequestDto.getVideo(),"timecapsule","video");
        String audioPath=fileService.uploadFiles(timeCapsuleRequestDto.getAudio(),"timecapsule","audio");

        String dueDateStr = timeCapsuleRequestDto.getDueDate();
        // 포맷터
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");

        // 문자열 -> Date
        Date dueDate = formatter.parse(dueDateStr); //ParseException 던짐

        TimeCapsuleEntity timeCapsuleEntity = TimeCapsuleEntity.builder()
                .writer(userId)
                .title(timeCapsuleRequestDto.getTitle())
                .content(timeCapsuleRequestDto.getContent())
                .dueDate(dueDate)
                .image(imagePath)
                .video(videoPath)
                .audio(audioPath)
                .ARMarkerId(timeCapsuleRequestDto.getARMarkerId())
                .build();

        timeCapsuleRepository.insert(timeCapsuleEntity);

        TimeCapsuleEntity newTimeCapsule = timeCapsuleRepository.selectById(timeCapsuleEntity.getTimeCapsuleId());

        ArrayList<ParticipantEntity> participantEntities = new ArrayList<>();
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

        Date date = timeCapsuleEntity.getDate();
        Date dueDate = timeCapsuleEntity.getDueDate();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateStr = dateFormat.format(date);
        String dueDateStr = dateFormat.format(dueDate);

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
                .date(dateStr)
                .dueDate(dueDateStr)
                .participants(participants)
                .image(timeCapsuleEntity.getImage())
                .video(timeCapsuleEntity.getVideo())
                .audio(timeCapsuleEntity.getAudio())
                .ARMarkerId(timeCapsuleEntity.getARMarkerId())
                .build();
        return timeCapsuleResponseDto;
    }

    public void delete(int timeCapsuleId){
        TimeCapsuleEntity timeCapsuleEntity = timeCapsuleRepository.selectById(timeCapsuleId);
        List<ParticipantEntity> participantEntities = participantRepository.selectByTimeCapsuleId(timeCapsuleId);

        timeCapsuleRepository.delete(timeCapsuleId);
        for(ParticipantEntity participantEntity: participantEntities){
            int id = participantEntity.getParticipantId();
            participantRepository.delete(id);
        }
    }
}


package com.army.ardiary.dto;

import com.army.ardiary.domain.entity.ParticipantEntity;
import com.army.ardiary.domain.entity.TimeCapsuleEntity;
import lombok.Builder;

import java.util.ArrayList;

@Builder
public class TimeCapsuleResponseDto {
    TimeCapsuleEntity timeCapsule;
    ArrayList<ParticipantEntity> participants;
}

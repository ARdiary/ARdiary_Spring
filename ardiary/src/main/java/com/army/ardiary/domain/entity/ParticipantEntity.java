package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantEntity {

    private int participantId;

    private int timeCapsuleId;

    private int userId;
}

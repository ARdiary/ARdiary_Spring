package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.ParticipantEntity;

import java.util.ArrayList;

public interface ParticipantRepository {

    int insert(ParticipantEntity participantEntity);

    ParticipantEntity selectById(int id);

    int update(ParticipantEntity participantEntity);

    int delete(int id);

    ArrayList<ParticipantEntity> selectByTimeCapsuleId(int timeCapsuleId);
}

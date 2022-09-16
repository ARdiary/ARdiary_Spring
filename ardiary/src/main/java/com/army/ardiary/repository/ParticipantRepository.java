package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.ParticipantEntity;

public interface ParticipantRepository {

    int insert(ParticipantEntity participantEntity);

    ParticipantEntity selectById(int id);

    int update(ParticipantEntity participantEntity);

    int delete(int id);
}

package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.ParticipantEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@Mapper
public interface ParticipantRepository {

    int insert(ParticipantEntity participantEntity);

    ParticipantEntity selectById(int id);

    int update(ParticipantEntity participantEntity);

    int delete(int id);

    ArrayList<ParticipantEntity> selectByTimeCapsuleId(int timeCapsuleId);
}

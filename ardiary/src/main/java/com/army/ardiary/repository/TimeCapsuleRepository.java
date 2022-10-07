package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.TimeCapsuleEntity;

public interface TimeCapsuleRepository {

    int insert(TimeCapsuleEntity timeCapsuleEntity);

    TimeCapsuleEntity selectById(int timeCapsuleId);

    int update(TimeCapsuleEntity timeCapsuleEntity);

    int delete(int timeCapsuleId);
}

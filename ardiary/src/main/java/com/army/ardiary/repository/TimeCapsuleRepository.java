package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.TimeCapsuleEntity;

public interface TimeCapsuleRepository {

    TimeCapsuleEntity insert(TimeCapsuleEntity timeCapsuleEntity);

    int selectById(int timeCapsuleId);

    int update(TimeCapsuleEntity timeCapsuleEntity);

    int delete(int timeCapsuleId);
}

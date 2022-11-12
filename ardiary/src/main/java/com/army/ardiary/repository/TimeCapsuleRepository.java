package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.TimeCapsuleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TimeCapsuleRepository {

    int insert(TimeCapsuleEntity timeCapsuleEntity);

    TimeCapsuleEntity selectById(int timeCapsuleId);
    TimeCapsuleEntity selectByMarker(int markerId);

    int update(TimeCapsuleEntity timeCapsuleEntity);

    int delete(int timeCapsuleId);


}

package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.MarkerImageEntity;
import com.army.ardiary.domain.entity.NotificationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface NotificationRepository {
    int insert(NotificationEntity notificationEntity);
    NotificationEntity selectById(int notificationId);
    ArrayList<NotificationEntity> selectByUser (int userId);
    int delete(int notificationId);
}

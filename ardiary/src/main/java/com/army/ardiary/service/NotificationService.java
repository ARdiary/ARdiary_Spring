package com.army.ardiary.service;

import com.army.ardiary.domain.entity.NotificationEntity;
import com.army.ardiary.repository.NotificationRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    List<NotificationEntity> findByUser(int userId){
        List<NotificationEntity> notificationEntities=notificationRepository.selectByUser(userId);
        return notificationEntities;
    }
    NotificationEntity findById(int notificationId){
        return notificationRepository.selectById(notificationId);
    }
    void createNewFollowerNotification(){

    }
    void createNewDiaryOfFollowerNotification(){

    }
    void createNewGuestBookOfFollowerNotification(){

    }
    void createTimecapsuleInvitationNotification(){

    }
}

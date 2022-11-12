package com.army.ardiary.service;

import com.army.ardiary.domain.entity.FollowEntity;
import com.army.ardiary.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final NotificationService notificationService;

    public boolean isFollow(int follower, int followee){
        int isF4F=followRepository.selectByFollow(follower,followee);
        if (isF4F!=0){
            return true;
        }
        else{
            return false;
        }
    }
    public void addFollow(int follower, int followee){
        FollowEntity followEntity = FollowEntity.builder()
                .follower(follower)
                .followee(followee)
                .build();

        followRepository.insert(followEntity);
        notificationService.createNewFollowerNotification(follower, followee);
    }

    public void deleteFollow(int followId) {
        followRepository.delete(followId);
    }
}

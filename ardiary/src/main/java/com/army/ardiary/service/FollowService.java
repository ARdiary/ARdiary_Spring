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
    private final UserService userService;
    public boolean isFollow(int follower, int followee){
        int isF4F=followRepository.isFollow(follower,followee);
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
        userService.setFollowNumByAddFollow(follower, followee);
    }

    public void deleteFollow(int followId) {
        FollowEntity followEntity=followRepository.selectById(followId);
        userService.setFollowNumByDeleteFollow(followEntity.getFollower(),followEntity.getFollowee() );
        followRepository.delete(followId);
    }
    public void deleteFollow(int follower, int followee) {
        followRepository.deleteByFollow(follower,followee);
        userService.setFollowNumByDeleteFollow(follower, followee);
    }
}

package com.army.ardiary.service;

import com.army.ardiary.domain.entity.FollowEntity;
import com.army.ardiary.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    public void addFollow(int follower, int followee){
        FollowEntity followEntity = FollowEntity.builder()
                .follower(follower)
                .followee(followee)
                .build();

        followRepository.insert(followEntity);
    }
}

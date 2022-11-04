package com.army.ardiary.service;

import com.army.ardiary.domain.entity.FollowEntity;
import com.army.ardiary.domain.entity.UserEntity;
import com.army.ardiary.dto.FollowDto;
import com.army.ardiary.repository.FollowRepository;
import com.army.ardiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    public List<FollowDto> findFollowingList(int userId){

        List<FollowEntity> follows = followRepository.selectByFollower(userId);
        List<FollowDto> followings = new ArrayList<>();

        for(FollowEntity follow: follows) {

            int followee = follow.getFollowee();
            UserEntity userEntity = userRepository.selectById(followee);

            FollowDto following = FollowDto.builder()
                    .followId(follow.getFollowId())
                    .userId(followee)
                    .nickname(userEntity.getNickname())
                    .profileImage(userEntity.getProfileImage())
                    .build();
            followings.add(following);

        }
        return followings;
    }

    public List<FollowDto> findFollowerList(int userId){

        List<FollowEntity> follows = followRepository.selectByFollower(userId);
        List<FollowDto> followers = new ArrayList<>();

        for(FollowEntity follow: follows) {

            int followerUserId = follow.getFollower();
            UserEntity userEntity = userRepository.selectById(followerUserId);

            FollowDto follower = FollowDto.builder()
                    .followId(follow.getFollowId())
                    .userId(followerUserId)
                    .nickname(userEntity.getNickname())
                    .profileImage(userEntity.getProfileImage())
                    .build();
            followers.add(follower);

        }
        return followers;
    }
}

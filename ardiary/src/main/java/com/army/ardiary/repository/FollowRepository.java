package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.FollowEntity;

import java.util.List;

public interface FollowRepository {
    int insert(FollowEntity followEntity);
    FollowEntity selectById(int followId);
    int update(FollowEntity followEntity);
    int delete(int followId);
    List<FollowEntity> selectByFollower(int follower);
    List<FollowEntity> selectByFollowee(int follower);
}

package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowEntity {
    private int followId;
    private int follower; //팔로우하는 user
    private int followee; //팔로우 받는 user
}

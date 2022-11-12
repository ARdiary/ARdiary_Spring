package com.army.ardiary.dto;

import lombok.Builder;

@Builder
public class FollowDto {
    int followId;
    int userId;
    String nickname;
    String profileImage;
}

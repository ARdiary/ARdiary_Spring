package com.army.ardiary.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FollowDto {
    int followId;
    int userId;
    String nickname;
    String profileImage;
}

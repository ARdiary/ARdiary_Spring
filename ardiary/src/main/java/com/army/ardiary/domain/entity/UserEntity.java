package com.army.ardiary.domain.entity;

import com.army.ardiary.controller.TimeCapsuleController;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
public class UserEntity {
    private int userId;
    private String email;
    private String profileImage;
    private String nickname;
    private int followingNum;
    private int followerNum;
    private LocalDateTime joinDate;
    private LocalDateTime withdrawalDate;
}

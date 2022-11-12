package com.army.ardiary.domain.entity;

import com.army.ardiary.controller.TimeCapsuleController;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
@Data
@Builder
public class UserEntity {
    private int userId;
    private String email;
    private String profileImage;
    private String nickname;
    private int followingNum;
    private int followerNum;
    private Timestamp joinDate;
    private Timestamp withdrawalDate;
}

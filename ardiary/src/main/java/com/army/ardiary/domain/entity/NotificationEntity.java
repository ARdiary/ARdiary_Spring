package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationEntity {
    private int notificationId;
    private int userId;
    private String notice;
}

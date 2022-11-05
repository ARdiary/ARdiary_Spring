package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Builder
public class TimeCapsuleEntity {
    private int timeCapsuleId;
    private int writer;
    private Date date;
    private String title;
    private String content;
    private Timestamp dueDate;
    private String image;
    private String video;
    private String audio;
    private int ARMarkerId;
}

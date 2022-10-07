package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class TimeCapsuleEntity {
    private int timeCapsuleId;
    private int writer;
    private LocalDate date;
    private String title;
    private String content;
    private LocalDate dueDate;
    private String image;
    private String video;
    private String audio;
    private String cameraARId;
}

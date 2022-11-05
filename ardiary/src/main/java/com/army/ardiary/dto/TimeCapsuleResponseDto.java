package com.army.ardiary.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
@Data
@Builder
public class TimeCapsuleResponseDto {
    int timeCapsuleId;
    String writer;
    String title;
    String content;
    Date date;
    Timestamp dueDate;
    List<String> participants;
    String image;
    String video;
    String audio;
    int ARMarkerId;
}

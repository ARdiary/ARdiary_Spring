package com.army.ardiary.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class TimeCapsuleResponseDto {
    int timeCapsuleId;
    String writer;
    String title;
    String content;
    String date;
    String dueDate;
    List<String> participants; //참가자 닉네임리스트
    String image;
    String video;
    String audio;
    int ARMarkerId;
}

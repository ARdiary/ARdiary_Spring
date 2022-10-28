package com.army.ardiary.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@Builder
@RequiredArgsConstructor
public class DiaryDto {
    private int diaryId;
    private String writer;
    private Date date;
    private String title;
    private String content;
    private String image;
    private String video;
    private String audio;
    private int ARMarkerId;
    private int privacyOption;
}

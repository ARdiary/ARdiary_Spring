package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

//jdbc 연동할때 date 는 sql.date 타입으로 가져오는게 좋다고 함
//자바 8 이후에서는 java.util.Date의 사용을 권고하지 않는다고 함
import java.sql.Date;

@Data
@Builder
public class DiaryEntity {
    private int diaryId;
    private int writer;
    private Date date;
    private String title;
    private String content;
    private String image;
    private String video;
    private String audio;
    private int cameraARId;
    private int privacyOption;
}


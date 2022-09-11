package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Builder
@Data
public class GuestBookEntity {

    private int guestBookId;

    private int writer;

    private LocalDate date;

    private String content;

    private int likeNum;

    private int cameraARId;
}

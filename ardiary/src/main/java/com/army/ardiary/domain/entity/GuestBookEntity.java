package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class GuestBookEntity {

    private int guestBookId;

    private int writer;

    private Date date;

    private String content;

    private int likeNum;

    private int ARMarkerId;
}

package com.army.ardiary.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class GuestBookDto {
    int guestBookId;

    String writer;

    Date date;

    String content;

    int likeNum;

    int ARMarkerId;
}

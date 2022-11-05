package com.army.ardiary.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

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

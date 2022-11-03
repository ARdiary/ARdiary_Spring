package com.army.ardiary.dto;

import lombok.Data;

import java.util.ArrayList;
@Data
public class TimeCapsuleDto {
    String title;
    String content;
    String date;
    String dueDate;
    ArrayList<String> participants;
    String image;
    String video;
    String audio;
}

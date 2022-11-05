package com.army.ardiary.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
@Data
public class TimeCapsuleRequestDto {
    String writer;
    String title;
    String content;
    String date;
    String dueDate;
    ArrayList<String> participants; //참가자 닉네임 리스트
    MultipartFile[] image;
    MultipartFile[] video;
    MultipartFile[] audio;
    int ARMarkerId;
}

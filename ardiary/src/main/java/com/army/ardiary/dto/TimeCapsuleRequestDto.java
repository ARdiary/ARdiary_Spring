package com.army.ardiary.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
@Data
public class TimeCapsuleRequestDto {
    String writer;
    String title;
    String content;
    String dueDate;
    ArrayList<String> participants;
    MultipartFile[] image;
    MultipartFile[] video;
    MultipartFile[] audio;
    int ARMarkerId;
}

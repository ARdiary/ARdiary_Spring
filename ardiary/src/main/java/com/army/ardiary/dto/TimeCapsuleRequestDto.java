package com.army.ardiary.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Data
public class TimeCapsuleRequestDto {
    String writer;
    String title;
    String content;
    Timestamp dueDate;
    List<String> participants;
    MultipartFile[] image;
    MultipartFile[] video;
    MultipartFile[] audio;
    int ARMarkerId;
}

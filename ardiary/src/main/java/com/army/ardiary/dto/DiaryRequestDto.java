package com.army.ardiary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class DiaryRequestDto {
    String title;
    String content;
    MultipartFile[] images;
    MultipartFile[] videos;
    MultipartFile[] audios;
    int privacyOption;
    int cameraARId;
}

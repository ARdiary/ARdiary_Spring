package com.army.ardiary.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class DiaryRequestDto {
    String title;
    String content;
    MultipartFile[] images;
    MultipartFile[] videos;
    MultipartFile[] audios;
    int privacyOption;
}

package com.army.ardiary.dto;

import com.army.ardiary.domain.entity.CameraAREntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
@RequiredArgsConstructor
public class CameraARRequestDto {
    MultipartFile specifyImg;
    double latitude;
    double longitude;
    int markerId;
}

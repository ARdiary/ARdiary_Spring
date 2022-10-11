package com.army.ardiary.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
public class ARMarkerRequestDto {
    MultipartFile specifyImg;
    double latitude;
    double longitude;
    int markerImageId;
    String contentType;
}

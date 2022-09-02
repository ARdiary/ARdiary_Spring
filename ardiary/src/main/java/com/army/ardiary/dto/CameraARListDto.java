package com.army.ardiary.dto;

import com.army.ardiary.domain.entity.CameraAREntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
@Data
@RequiredArgsConstructor
public class CameraARListDto {
    ArrayList<CameraAREntity> cameraARList;
}

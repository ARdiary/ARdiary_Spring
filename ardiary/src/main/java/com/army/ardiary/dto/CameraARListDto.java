package com.army.ardiary.dto;

import com.army.ardiary.domain.entity.CameraAREntity;
import com.army.ardiary.domain.entity.MarkerEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class CameraARListDto {
    ArrayList<Map.Entry<CameraAREntity, MarkerEntity>> cameraARList;
}

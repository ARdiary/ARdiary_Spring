package com.army.ardiary.dto;

import com.army.ardiary.domain.entity.CameraAREntity;
import com.army.ardiary.domain.entity.MarkerEntity;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@Builder
public class MarkerListDto {
    ArrayList<Map.Entry<MarkerEntity, CameraAREntity>> markerList;
    //cameraList.get(0).getKey() 이렇게 엔트리 접근 가능
}

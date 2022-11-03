package com.army.ardiary.dto;

import com.army.ardiary.domain.entity.ARMarkerEntity;
import com.army.ardiary.domain.entity.MarkerImageEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
@Builder
public class ARMarkerListDto {
    ArrayList<Map.Entry<MarkerImageEntity, ARMarkerEntity>> markerList;
    //cameraList.get(0).getKey() 이렇게 엔트리 접근 가능
}

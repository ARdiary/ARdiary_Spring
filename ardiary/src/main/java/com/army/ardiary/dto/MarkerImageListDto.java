package com.army.ardiary.dto;

import com.army.ardiary.domain.entity.MarkerImageEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class MarkerImageListDto {
    ArrayList<MarkerImageEntity> markerImageList;
}

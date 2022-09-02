package com.army.ardiary.dto;

import com.army.ardiary.domain.entity.MarkerEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class MarkerListDto {
    ArrayList<MarkerEntity> markerList;
}

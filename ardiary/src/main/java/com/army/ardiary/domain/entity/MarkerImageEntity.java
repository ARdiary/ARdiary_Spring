package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarkerImageEntity {
    private int markerImageId;
    private String markerImage;
}

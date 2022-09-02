package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarkerEntity {
    private int markerId;
    private String markerImage;
}

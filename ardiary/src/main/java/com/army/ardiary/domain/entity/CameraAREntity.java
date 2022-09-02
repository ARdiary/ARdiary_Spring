package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CameraAREntity {
private int cameraARId;
private String specifyImg;
private double latitude;
private double longitude;
private int markerId;
}

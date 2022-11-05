package com.army.ardiary.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ARMarkerEntity {
private int ARMarkerId;
private String specifyImg;
private double latitude;
private double longitude;
private int markerImageId;
private String contentType;
private String address;
}

package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.ARMarkerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface ARMarkerRepository {
    int insert(ARMarkerEntity ARMarkerEntity);
    ARMarkerEntity selectById(int ARMarkerId);
    ArrayList<ARMarkerEntity> selectByLatLon(double latitude, double longitude, int distance);
    ARMarkerEntity selectByMarker(int markerImageId);
    ArrayList<ARMarkerEntity> selectAll();
    int update(ARMarkerEntity ARMarkerEntity);
    int delete(int ARMarkerId);
}

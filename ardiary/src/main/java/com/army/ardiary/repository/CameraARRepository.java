package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.CameraAREntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface CameraARRepository {
    int insert(CameraAREntity cameraAREntity);
    CameraAREntity selectById(int cameraARId);
    ArrayList<CameraAREntity> selectByLatLon(double latitude, double longitude, int distance);
    CameraAREntity selectByMarker(int markerId);
    ArrayList<CameraAREntity> selectAll();
    int update(CameraAREntity cameraAREntity);
    int delete(int cameraARId);
}

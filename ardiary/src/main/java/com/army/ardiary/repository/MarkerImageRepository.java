package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.MarkerImageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@Mapper
public interface MarkerImageRepository {
    int insert(MarkerImageEntity markerImageEntity);

    MarkerImageEntity selectById(int markerImageId);
    ArrayList<MarkerImageEntity> selectByIds(int[] markerImageIds);

    int update(MarkerImageEntity markerImageEntity);

    int delete(int markerImageId);

    ArrayList<MarkerImageEntity> selectAll();
}

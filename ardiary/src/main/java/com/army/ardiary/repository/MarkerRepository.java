package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.MarkerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@Mapper
public interface MarkerRepository {
    int insert(MarkerEntity markerEntity);

    MarkerEntity selectById(int markerId);
    ArrayList<MarkerEntity> selectByIds(int[] markerIds);

    int update(MarkerEntity markerEntity);

    int delete(int markerId);

    ArrayList<MarkerEntity> selectAll();
}

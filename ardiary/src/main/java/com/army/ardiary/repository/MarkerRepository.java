package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.MarkerEntity;

public interface MarkerRepository {
    int insert(MarkerEntity markerEntity);

    MarkerEntity selectById(int markerId);

    int update(MarkerEntity markerEntity);

    int delete(int markerId);
}

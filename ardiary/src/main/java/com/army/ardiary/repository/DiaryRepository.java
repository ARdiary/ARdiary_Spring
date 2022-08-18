package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.DiaryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DiaryRepository {
    int insert(DiaryEntity diaryEntity);
    DiaryEntity selectById(int diaryId);
    DiaryEntity selectByWriter(int userId);
    DiaryEntity selectByCameraARId(int cameraARId);
    int update(DiaryEntity diaryEntity);
    int delete(int diaryId);
}

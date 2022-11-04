package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.LikeDiaryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeDiaryRepository {
    int insert(LikeDiaryEntity likeDiaryEntity);
    LikeDiaryEntity select(int likeDiaryId);
    int update(LikeDiaryEntity likeDiaryEntity);
    int delete(int likeGuestBookId);
}

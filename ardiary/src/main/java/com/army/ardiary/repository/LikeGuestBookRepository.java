package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.LikeDiaryEntity;
import com.army.ardiary.domain.entity.LikeGuestBookEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LikeGuestBookRepository {
    int insert(LikeGuestBookEntity likeGuestBookEntity);
    LikeGuestBookEntity select(int likeGuestBookId);
    int update(LikeGuestBookEntity likeGuestBookEntity);
    int delete(int likeGuestBookId);

    List<LikeGuestBookEntity> selectByUser(int userId);
}

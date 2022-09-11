package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.GuestBookEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GuestBookRepository {
    int insert (GuestBookEntity guestBookEntity);

    GuestBookEntity selectById(int guestBookId);

    int update(GuestBookEntity guestBookEntity);

    int delete(int guestBookId);
}

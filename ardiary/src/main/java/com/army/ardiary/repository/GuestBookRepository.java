package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.GuestBookEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface GuestBookRepository {
    int insert (GuestBookEntity guestBookEntity);

    GuestBookEntity selectById(int guestBookId);
    GuestBookEntity selectByMarker(int markerId);

    int update(GuestBookEntity guestBookEntity);

    int delete(int guestBookId);

    ArrayList<GuestBookEntity> selectByUser(int userId);
}

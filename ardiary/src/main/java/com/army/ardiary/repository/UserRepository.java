package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    int insert(UserEntity userEntity);
    UserEntity selectById(int userId);
    int update(UserEntity userEntity);
    int delete(int userId);
    UserEntity selectByEmail(String email);
}

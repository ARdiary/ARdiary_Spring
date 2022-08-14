package com.army.ardiary.repository;

import com.army.ardiary.domain.entity.FollowEntity;
import com.army.ardiary.domain.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@MapperScan(basePackages = "com.army.ardiary.repository")
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Test
    void insert() {
        // Arrange
        FollowEntity expected = FollowEntity.builder()
                .follower(1)
                .followee(2)
                .build();

        //Act
        int insertCount = followRepository.insert(expected);

        //Assert
        int id = expected.getFollowId();
        FollowEntity actual = followRepository.selectById(id);
        assertEquals(expected, actual);
    }

    @Test
    void selectById() {
        //Arrange
        FollowEntity expected = FollowEntity.builder()
                .follower(1)
                .followee(2)
                .build();
        int insertCount = followRepository.insert(expected);
        assertEquals(1, insertCount);

        int id = expected.getFollowId();

        //Act
        FollowEntity actual = followRepository.selectById(id);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        //Arrange
        FollowEntity followEntity = FollowEntity.builder()
                .follower(1)
                .followee(2)
                .build();

        int insertCount = followRepository.insert(followEntity);
        assertEquals(1, insertCount);

        followEntity.setFollowee(1);

        //Act
        int updateCount = followRepository.update(followEntity);

        //Assert
        assertEquals(1, updateCount);
        assertEquals(1, followEntity.getFollowee());
    }

    @Test
    void delete() {
        FollowEntity followEntity = FollowEntity.builder()
                .follower(1)
                .followee(2)
                .build();

        int insertCount = followRepository.insert(followEntity);
        assertEquals(1, insertCount);

        int id = followEntity.getFollowId();

        //Act
        int deleteCount = followRepository.delete(id);

        //Assert
        assertEquals(1, deleteCount);
        assertNull(followRepository.selectById(id));
    }

    @Test
    void selectByFollower() {

    }

    @Test
    void selectByFollowee() {

    }
}
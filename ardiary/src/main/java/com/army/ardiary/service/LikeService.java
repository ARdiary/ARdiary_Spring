package com.army.ardiary.service;

import com.army.ardiary.domain.entity.DiaryEntity;
import com.army.ardiary.domain.entity.LikeDiaryEntity;
import com.army.ardiary.repository.DiaryRepository;
import com.army.ardiary.repository.LikeDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDiaryRepository likeDiaryRepository;
    private final DiaryRepository diaryRepository;
    public void addLikeDiary(int userId, int diaryId){
        LikeDiaryEntity likeDiaryEntity = LikeDiaryEntity.builder()
                        .userId(userId)
                        .diaryId(diaryId)
                        .build();

        likeDiaryRepository.insert(likeDiaryEntity);

        //해당 다이어리의 좋아요 수 +1
    }

    public void deleteLikeDiary(int userId, int diaryId){
        likeDiaryRepository.delete(userId, diaryId);
        //해당 다이어리 좋아요 수 -1
    }
}

package com.army.ardiary.service;

import com.army.ardiary.domain.entity.GuestBookEntity;
import com.army.ardiary.domain.entity.LikeDiaryEntity;
import com.army.ardiary.domain.entity.LikeGuestBookEntity;
import com.army.ardiary.repository.DiaryRepository;
import com.army.ardiary.repository.GuestBookRepository;
import com.army.ardiary.repository.LikeDiaryRepository;
import com.army.ardiary.repository.LikeGuestBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeDiaryRepository likeDiaryRepository;
    private final LikeGuestBookRepository likeGuestBookRepository;
    private final DiaryRepository diaryRepository;
    private final GuestBookRepository guestBookRepository;
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

    public void addLikeGuestBook(int userId, int guestBookId){
        LikeGuestBookEntity likeGuestBookEntity = LikeGuestBookEntity.builder()
                .userId(userId)
                .guestBookId(guestBookId)
                .build();

        likeGuestBookRepository.insert(likeGuestBookEntity);

        GuestBookEntity guestBookEntity = guestBookRepository.selectById(guestBookId);

        int orig = guestBookEntity.getLikeNum();
        guestBookEntity.setLikeNum(orig+1);
    }

    public void deleteLikeGuestBook(int likeGuestBookId){
        likeGuestBookRepository.delete(likeGuestBookId);
    }

}

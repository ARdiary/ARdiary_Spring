package com.army.ardiary.service;

import com.army.ardiary.domain.entity.DiaryEntity;
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
    private final NotificationService notificationService;
    public void addLikeDiary(int userId, int diaryId){
        LikeDiaryEntity likeDiaryEntity = LikeDiaryEntity.builder()
                        .userId(userId)
                        .diaryId(diaryId)
                        .build();

        likeDiaryRepository.insert(likeDiaryEntity);
        notificationService.createLikedNotification(likeDiaryEntity);

        //해당 다이어리의 좋아요 수 +1
    }

    public void deleteLikeDiary(int likeGuestBookId){
        likeDiaryRepository.delete(likeGuestBookId);
        //해당 다이어리 좋아요 수 -1
    }

    public void addLikeGuestBook(int userId, int guestBookId){
        LikeGuestBookEntity likeGuestBookEntity = LikeGuestBookEntity.builder()
                .userId(userId)
                .guestBookId(guestBookId)
                .build();

        likeGuestBookRepository.insert(likeGuestBookEntity);

        GuestBookEntity guestBookEntity = guestBookRepository.selectById(guestBookId);

        //해당 방명록 좋아요수+1
        int orig = guestBookEntity.getLikeNum();
        guestBookEntity.setLikeNum(orig+1);
        guestBookRepository.update(guestBookEntity);
        notificationService.createLikedNotification(likeGuestBookEntity);
    }

    public void deleteLikeGuestBook(int likeGuestBookId){
        LikeGuestBookEntity likeGuestBookEntity=likeGuestBookRepository.select(likeGuestBookId);
        likeGuestBookRepository.delete(likeGuestBookId);

        GuestBookEntity guestBookEntity = guestBookRepository.selectById(likeGuestBookEntity.getGuestBookId());

        //해당 방명록 좋아요수-1
        guestBookEntity.setLikeNum(guestBookEntity.getLikeNum()-1);
        guestBookRepository.update(guestBookEntity);
    }

}

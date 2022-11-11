package com.army.ardiary.service;

import com.army.ardiary.domain.entity.*;
import com.army.ardiary.repository.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
private final DiaryRepository diaryRepository;
private final GuestBookRepository guestBookRepository;

    public List<NotificationEntity> findByUser(int userId){
        List<NotificationEntity> notificationEntities=notificationRepository.selectByUser(userId);
        return notificationEntities;
    }
    public NotificationEntity findById(int notificationId){
        return notificationRepository.selectById(notificationId);
    }

    public int deleteNotification(int notificationId){

        return notificationRepository.delete(notificationId);
    }
    public boolean isUser(int userId, int notificationId){
        return userId==notificationRepository.selectById(notificationId).getUserId();
    }
    public void createNewFollowerNotification(int followerId, int followeeId){
        String followerNickname=userRepository.selectById(followerId).getNickname();
        NotificationEntity newNotificationEntity=NotificationEntity.builder()
                .userId(followeeId).notice(followerNickname+"님이 회원님을 팔로우하기 시작했습니다")
                .build();
        notificationRepository.insert(newNotificationEntity);
    }
    public void createTimecapsuleInvitationNotification(TimeCapsuleEntity timeCapsuleEntity, List<ParticipantEntity> participants){

        for(ParticipantEntity participantEntity: participants){
            String writerNickname=userRepository.selectById(timeCapsuleEntity.getWriter()).getNickname();
            NotificationEntity newNotificationEntity=NotificationEntity.builder()
                    .userId(participantEntity.getUserId())
                    .notice(writerNickname+"님으로부터 타임캡슐 '"+timeCapsuleEntity.getTitle()+"' 에 초대되었습니다")
                    .build();
            notificationRepository.insert(newNotificationEntity);
        }
    }
    public void createTimecapsuleOpenNotification(TimeCapsuleEntity timeCapsuleEntity,List<ParticipantEntity> participants){
        for(ParticipantEntity participantEntity: participants){
            String writerNickname=userRepository.selectById(timeCapsuleEntity.getWriter()).getNickname();
            NotificationEntity newNotificationEntity=NotificationEntity.builder()
                    .userId(participantEntity.getUserId())
                    .notice(writerNickname+"타임캡슐 '"+timeCapsuleEntity.getTitle()+"' 이 오픈되었습니다")
                    .build();
            notificationRepository.insert(newNotificationEntity);
        }
    }
    public void createLikedNotification(LikeDiaryEntity likeDiaryEntity ){
        String nickname=userRepository.selectById(likeDiaryEntity.getUserId()).getNickname();
        DiaryEntity diaryEntity=diaryRepository.selectById(likeDiaryEntity.getDiaryId());
        NotificationEntity newNotificationEntity=NotificationEntity.builder()
                .userId(diaryEntity.getWriter())
                .notice(nickname+"님이 회원님의 일기'"+diaryEntity.getTitle()+"'을(를) 좋아합니다" )
                .build();
        notificationRepository.insert(newNotificationEntity);
    }
    public void createLikedNotification(LikeGuestBookEntity likeGuestBookEntity ){
        String nickname=userRepository.selectById(likeGuestBookEntity.getUserId()).getNickname();
        GuestBookEntity guestBookEntity=guestBookRepository.selectById(likeGuestBookEntity.getGuestBookId());
        NotificationEntity newNotificationEntity=NotificationEntity.builder()
                .userId(guestBookEntity.getWriter())
                .notice(nickname+"님이 회원님의 방명록'"+guestBookEntity.getContent()+"'을(를) 좋아합니다" )
                .build();
        notificationRepository.insert(newNotificationEntity);
    }

}

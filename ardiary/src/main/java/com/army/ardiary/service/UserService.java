package com.army.ardiary.service;

import com.army.ardiary.domain.entity.*;
import com.army.ardiary.dto.DiaryDto;
import com.army.ardiary.dto.FollowDto;
import com.army.ardiary.dto.GuestBookDto;
import com.army.ardiary.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final LikeDiaryRepository likeDiaryRepository;
    private final LikeGuestBookRepository likeGuestBookRepository;
    private final DiaryRepository diaryRepository;
    private final GuestBookRepository guestBookRepository;
    private final FileService fileService;

    public UserEntity getUserInfo(int userId){
        UserEntity userEntity = userRepository.selectById(userId);
        return userEntity;
    }

    public int getUserByNickName(String nickname){
        UserEntity userEntity = userRepository.selectByNickname(nickname);
        if(userEntity==null) {
            return 0;
        }else{
            int id = userEntity.getUserId();
            return id;
        }
    }

    public UserEntity changeNickName(int userId, String newNickName){
        UserEntity userEntity = userRepository.selectById(userId);

        UserEntity newUser = UserEntity.builder()
                .email(userEntity.getEmail())
                .profileImage(userEntity.getProfileImage())
                .nickname(newNickName)
                .followingNum(userEntity.getFollowingNum())
                .followerNum(userEntity.getFollowerNum())
                .joinDate(userEntity.getJoinDate())
                .build();

        userRepository.update(newUser);
        return newUser;
    }

    public UserEntity changeProfileImage(int userId, MultipartFile[] newProfileImage){

        String profileImagePath=fileService.uploadFiles(newProfileImage,"profile","image");

        UserEntity userEntity = userRepository.selectById(userId);

        UserEntity newUser = UserEntity.builder()
                .email(userEntity.getEmail())
                .profileImage(profileImagePath)
                .nickname(userEntity.getNickname())
                .followingNum(userEntity.getFollowingNum())
                .followerNum(userEntity.getFollowerNum())
                .joinDate(userEntity.getJoinDate())
                .build();

        userRepository.update(newUser);

        return newUser;
    }

    public List<FollowDto> findFollowingList(int userId){

        List<FollowEntity> follows = followRepository.selectByFollower(userId);
        List<FollowDto> followings = new ArrayList<>();

        for(FollowEntity follow: follows) {

            int followee = follow.getFollowee();
            UserEntity userEntity = userRepository.selectById(followee);

            FollowDto following = FollowDto.builder()
                    .followId(follow.getFollowId())
                    .userId(followee)
                    .nickname(userEntity.getNickname())
                    .profileImage(userEntity.getProfileImage())
                    .build();
            followings.add(following);

        }
        return followings;
    }

    public List<FollowDto> findFollowerList(int userId){

        List<FollowEntity> follows = followRepository.selectByFollower(userId);
        List<FollowDto> followers = new ArrayList<>();

        for(FollowEntity follow: follows) {

            int followerUserId = follow.getFollower();
            UserEntity userEntity = userRepository.selectById(followerUserId);

            FollowDto follower = FollowDto.builder()
                    .followId(follow.getFollowId())
                    .userId(followerUserId)
                    .nickname(userEntity.getNickname())
                    .profileImage(userEntity.getProfileImage())
                    .build();
            followers.add(follower);

        }
        return followers;
    }

    public List<DiaryDto> findLikeDiaryList(int userId){
        List<LikeDiaryEntity> likeDiaryEntities = likeDiaryRepository.selectByUser(userId);
        List<DiaryDto> diaries = new ArrayList<>();
        for(LikeDiaryEntity likeDiaryEntity: likeDiaryEntities){
            int diaryId = likeDiaryEntity.getDiaryId();
            DiaryEntity diaryEntity = diaryRepository.selectById(diaryId);
            int writerId = diaryEntity.getWriter();
            UserEntity writerEntity = userRepository.selectById(writerId);
            DiaryDto diary = DiaryDto.builder()
                    .diaryId(diaryId)
                    .writer(writerEntity.getNickname())
                    .date(diaryEntity.getDate())
                    .title(diaryEntity.getTitle())
                    .content(diaryEntity.getContent())
                    .image(diaryEntity.getImage())
                    .video(diaryEntity.getVideo())
                    .audio(diaryEntity.getAudio())
                    .ARMarkerId(diaryEntity.getARMarkerId())
                    .privacyOption(diaryEntity.getPrivacyOption())
                    .build();
            diaries.add(diary);
        }
        return diaries;
    }

    public List<GuestBookDto> findLikeGuestBookList(int userId){
        List<LikeGuestBookEntity> likeGuestBookEntities = likeGuestBookRepository.selectByUser(userId);
        List<GuestBookDto> guestbooks = new ArrayList<>();
        for(LikeGuestBookEntity likeGuestBookEntity: likeGuestBookEntities){
            int guestBookId = likeGuestBookEntity.getGuestBookId();
            GuestBookEntity guestBookEntity = guestBookRepository.selectById(guestBookId);
            int writerId = guestBookEntity.getWriter();
            UserEntity writerEntity = userRepository.selectById(writerId);
            GuestBookDto guestbook = GuestBookDto.builder()
                    .guestBookId(guestBookId)
                    .writer(writerEntity.getNickname())
                    .date(guestBookEntity.getDate())
                    .content(guestBookEntity.getContent())
                    .likeNum(guestBookEntity.getLikeNum())
                    .ARMarkerId(guestBookEntity.getARMarkerId())
                    .build();
            guestbooks.add(guestbook);
        }
        return guestbooks;
    }
}

package com.army.ardiary.service;

import com.army.ardiary.domain.entity.GuestBookEntity;
import com.army.ardiary.dto.GuestBookContentDto;
import com.army.ardiary.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GuestBookService {

    private final GuestBookRepository guestBookRepository;

    public GuestBookEntity createGuestBook(int userId, GuestBookContentDto content){

        GuestBookEntity guestBookEntity = GuestBookEntity.builder()
                        .writer(userId)
                        .content(content.getContent())
                        .likeNum(0)
                        .ARMarkerId(guestBookInfo.getARMarkerId())
                        .build();

        guestBookRepository.insert(guestBookEntity);
        GuestBookEntity newGuestBook = guestBookRepository.selectById(guestBookEntity.getGuestBookId());

        return newGuestBook;
    }
    public GuestBookEntity findGuestBook(int guestBookId){
        return guestBookRepository.selectById(guestBookId);
    }

    public ArrayList<GuestBookEntity> findGuestBookListByUser(int userId){
        return guestBookRepository.selectByUser(userId);
    }

    public GuestBookEntity updateContent(int id, String content){
        GuestBookEntity guestBookEntity = guestBookRepository.selectById(id);
        guestBookEntity.setContent(content);
        guestBookRepository.update(guestBookEntity);
        return guestBookEntity;
    }
    public GuestBookEntity delete(int id){
        GuestBookEntity guestBookEntity = guestBookRepository.selectById(id);
        guestBookRepository.delete(id);
        return guestBookEntity;
    }

    public boolean isUser(int tokenUserId, int guestBookId){
        GuestBookEntity guestBookEntity = guestBookRepository.selectById(guestBookId);
        int userId = guestBookEntity.getWriter();
        return userId == tokenUserId;
    }
}

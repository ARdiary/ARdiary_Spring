package com.army.ardiary.service;

import com.army.ardiary.domain.entity.GuestBookEntity;
import com.army.ardiary.dto.GuestBookInfoDto;
import com.army.ardiary.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GuestBookService {

    private final GuestBookRepository guestBookRepository;

    public GuestBookEntity createGuestBook(int userId, GuestBookInfoDto guestBookInfo){

        GuestBookEntity guestBookEntity = GuestBookEntity.builder()
                        .writer(userId)
                        .content(guestBookInfo.getContent())
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
}

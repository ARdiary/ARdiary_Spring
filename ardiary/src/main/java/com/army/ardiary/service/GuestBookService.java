package com.army.ardiary.service;

import com.army.ardiary.domain.entity.GuestBookEntity;
import com.army.ardiary.dto.GuestBookContentDto;
import com.army.ardiary.dto.GuestBookDto;
import com.army.ardiary.repository.GuestBookRepository;
import com.army.ardiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.error.Mark;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestBookService {

    private final GuestBookRepository guestBookRepository;
    private final MarkerService markerService;
    private final UserRepository userRepository;

    public GuestBookDto createGuestBook(int userId, GuestBookContentDto content){

        GuestBookEntity guestBookEntity = GuestBookEntity.builder()
                        .writer(userId)
                        .content(content.getContent())
                        .likeNum(0)
                        .ARMarkerId(content.getARMarkerId())
                        .build();

        guestBookRepository.insert(guestBookEntity);
        GuestBookEntity newGuestBook = guestBookRepository.selectById(guestBookEntity.getGuestBookId());

        return makeDto(newGuestBook);
    }
    public GuestBookDto findGuestBook(int guestBookId){
        return makeDto(guestBookRepository.selectById(guestBookId));
    }
    public GuestBookDto findByMarker(int markerId){
        return makeDto(guestBookRepository.selectByMarker(markerId));
    }
    public List<GuestBookDto> findGuestBookListByUser(int userId){
        ArrayList<GuestBookEntity> guestBookEntities=guestBookRepository.selectByUser(userId);
        List<GuestBookDto> guestBookDtoList=new ArrayList<>();
        for(GuestBookEntity guestBookEntity: guestBookEntities){
           guestBookDtoList.add(makeDto(guestBookEntity));
        }

        return guestBookDtoList;
    }

    public GuestBookDto updateContent(int id, String content){
        GuestBookEntity guestBookEntity = guestBookRepository.selectById(id);
        guestBookEntity.setContent(content);
        guestBookRepository.update(guestBookEntity);
        return makeDto(guestBookEntity);
    }
    public GuestBookDto delete(int id){
        GuestBookEntity guestBookEntity = guestBookRepository.selectById(id);
        guestBookRepository.delete(id);
        markerService.delete(guestBookEntity.getARMarkerId());
        return makeDto(guestBookEntity);
    }

    public boolean isUser(int tokenUserId, int guestBookId){
        GuestBookEntity guestBookEntity = guestBookRepository.selectById(guestBookId);
        int userId = guestBookEntity.getWriter();
        return userId == tokenUserId;
    }
    public GuestBookDto makeDto(GuestBookEntity guestBookEntity){
        GuestBookDto guestBookDto=GuestBookDto.builder()
                .guestBookId(guestBookEntity.getGuestBookId())
                .writer(userRepository.selectById(guestBookEntity.getWriter()).getNickname())
                .date(guestBookEntity.getDate())
                .ARMarkerId(guestBookEntity.getARMarkerId())
                .content(guestBookEntity.getContent())
                .likeNum(guestBookEntity.getLikeNum())
                .build();
        return guestBookDto;
    }
}

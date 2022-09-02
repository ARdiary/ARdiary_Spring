package com.army.ardiary.service;

import com.army.ardiary.domain.entity.MarkerEntity;
import com.army.ardiary.dto.MarkerListDto;
import com.army.ardiary.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MarkerService {

    private final TokenService tokenService;
    private final MarkerRepository markerRepository;

    public MarkerListDto findMarkerList(){

        ArrayList<MarkerEntity> markerList = markerRepository.selectAll();
        MarkerListDto markerListDto = MarkerListDto.builder()
                .markerList(markerList)
                .build();
       return markerListDto;

    }
}

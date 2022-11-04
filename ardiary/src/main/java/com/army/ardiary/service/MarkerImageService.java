package com.army.ardiary.service;

import com.army.ardiary.domain.entity.ARMarkerEntity;
import com.army.ardiary.domain.entity.MarkerImageEntity;
import com.army.ardiary.dto.MarkerImageListDto;
import com.army.ardiary.dto.MarkerListDto;
import com.army.ardiary.repository.ARMarkerRepository;
import com.army.ardiary.repository.MarkerImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarkerImageService {

    private final TokenService tokenService;
    private final MarkerImageRepository markerImageRepository;
    private final ARMarkerRepository ARMarkerRepository;

    public MarkerImageListDto findMarkerImageList(){

        ArrayList<MarkerImageEntity> markerList = markerImageRepository.selectAll();
        MarkerImageListDto markerImageListDto = MarkerImageListDto.builder()
                .markerImageList(markerList)
                .build();
       return markerImageListDto;
    }
    public MarkerListDto findMarkerList(){
        ArrayList<Map.Entry<MarkerImageEntity, ARMarkerEntity>> markerList=new ArrayList<>();
        ArrayList<ARMarkerEntity> cameraAREntities = ARMarkerRepository.selectAll();
        for(ARMarkerEntity ARMarkerEntity :cameraAREntities){
            MarkerImageEntity markerImageEntity = markerImageRepository.selectById(ARMarkerEntity.getMarkerImageId());

            markerList.add(new AbstractMap.SimpleEntry<>(markerImageEntity, ARMarkerEntity));
        }
        MarkerListDto markerListDto=MarkerListDto.builder()
                .markerList(markerList)
                .build();
        return markerListDto;
    }


    public MarkerListDto findMarkerListByLocation(double lat, double lon, int distance){
        ArrayList<Map.Entry<MarkerImageEntity, ARMarkerEntity>> markerList=new ArrayList<>();
        ArrayList<ARMarkerEntity> cameraAREntities = ARMarkerRepository.selectByLatLon(lat,lon,distance);
        for(ARMarkerEntity ARMarkerEntity :cameraAREntities){
            MarkerImageEntity markerImageEntity = markerImageRepository.selectById(ARMarkerEntity.getMarkerImageId());

            markerList.add(new AbstractMap.SimpleEntry<>(markerImageEntity, ARMarkerEntity));
        }
        MarkerListDto markerListDto=MarkerListDto.builder()
                .markerList(markerList)
                .build();
        return markerListDto;
    }
}

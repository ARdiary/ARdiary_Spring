package com.army.ardiary.service;

import com.army.ardiary.domain.entity.CameraAREntity;
import com.army.ardiary.domain.entity.MarkerEntity;
import com.army.ardiary.dto.MarkerImageListDto;
import com.army.ardiary.dto.MarkerListDto;
import com.army.ardiary.repository.CameraARRepository;
import com.army.ardiary.repository.MarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarkerService {

    private final TokenService tokenService;
    private final MarkerRepository markerRepository;
    private final CameraARRepository cameraARRepository;

    public MarkerImageListDto findMarkerImageList(){

        ArrayList<MarkerEntity> markerList = markerRepository.selectAll();
        MarkerImageListDto markerImageListDto = MarkerImageListDto.builder()
                .markerImageList(markerList)
                .build();
       return markerImageListDto;
    }
    public MarkerListDto findMarkerList(){
        ArrayList<Map.Entry<MarkerEntity,CameraAREntity>> markerList=new ArrayList<>();
        ArrayList<CameraAREntity> cameraAREntities = cameraARRepository.selectAll();
        for(CameraAREntity cameraAREntity :cameraAREntities){
            MarkerEntity markerEntity=markerRepository.selectById(cameraAREntity.getMarkerId());

            markerList.add(new AbstractMap.SimpleEntry<>(markerEntity,cameraAREntity));
        }
        MarkerListDto markerListDto=MarkerListDto.builder()
                .markerList(markerList)
                .build();
        return markerListDto;
    }


    public MarkerListDto findMarkerListByLocation(double lat, double lon, int distance){
        ArrayList<Map.Entry<MarkerEntity,CameraAREntity>> markerList=new ArrayList<>();
        ArrayList<CameraAREntity> cameraAREntities = cameraARRepository.selectByLatLon(lat,lon,distance);
        for(CameraAREntity cameraAREntity :cameraAREntities){
            MarkerEntity markerEntity=markerRepository.selectById(cameraAREntity.getMarkerId());

            markerList.add(new AbstractMap.SimpleEntry<>(markerEntity,cameraAREntity));
        }
        MarkerListDto markerListDto=MarkerListDto.builder()
                .markerList(markerList)
                .build();
        return markerListDto;
    }
}

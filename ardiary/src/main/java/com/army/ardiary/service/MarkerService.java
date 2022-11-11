package com.army.ardiary.service;

import com.army.ardiary.domain.entity.ARMarkerEntity;
import com.army.ardiary.domain.entity.GuestBookEntity;
import com.army.ardiary.domain.entity.MarkerImageEntity;
import com.army.ardiary.dto.ARMarkerRequestDto;
import com.army.ardiary.dto.MarkerImageListDto;
import com.army.ardiary.dto.ARMarkerListDto;
import com.army.ardiary.repository.ARMarkerRepository;
import com.army.ardiary.repository.MarkerImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarkerService {

    private final TokenService tokenService;
    private final MarkerImageRepository markerImageRepository;
    private final ARMarkerRepository aRMarkerRepository;
    private final FileService fileService;

    public MarkerImageListDto findMarkerImageList(){

        ArrayList<MarkerImageEntity> markerList = markerImageRepository.selectAll();
        MarkerImageListDto markerImageListDto = MarkerImageListDto.builder()
                .markerImageList(markerList)
                .build();
       return markerImageListDto;
    }
    public ARMarkerListDto findMarkerList(){
        ArrayList<Map.Entry<MarkerImageEntity, ARMarkerEntity>> markerList=new ArrayList<>();
        ArrayList<ARMarkerEntity> ARmarkerEntities = aRMarkerRepository.selectAll();
        for(ARMarkerEntity ARMarkerEntity :ARmarkerEntities){
            MarkerImageEntity markerImageEntity = markerImageRepository.selectById(ARMarkerEntity.getMarkerImageId());

            markerList.add(new AbstractMap.SimpleEntry<>(markerImageEntity, ARMarkerEntity));
        }
        ARMarkerListDto allARMarkerListDto = ARMarkerListDto.builder()
                .markerList(markerList)
                .build();
        return allARMarkerListDto;
    }


    public ARMarkerListDto findMarkerListByLocation(double lat, double lon, int distance){
        ArrayList<Map.Entry<MarkerImageEntity, ARMarkerEntity>> markerList=new ArrayList<>();
        ArrayList<ARMarkerEntity> ARmarkerEntities = aRMarkerRepository.selectByLatLon(lat,lon,distance);
        for(ARMarkerEntity ARMarkerEntity :ARmarkerEntities){
            MarkerImageEntity markerImageEntity = markerImageRepository.selectById(ARMarkerEntity.getMarkerImageId());

            markerList.add(new AbstractMap.SimpleEntry<>(markerImageEntity, ARMarkerEntity));
        }
        ARMarkerListDto locARMarkerListDto = ARMarkerListDto.builder()
                .markerList(markerList)
                .build();
        return locARMarkerListDto;
    }
    public int createARMarker(ARMarkerRequestDto arMarkerRequestDto, int userId){
        MultipartFile[] multipartFile={arMarkerRequestDto.getSpecifyImg()};
       String imagePath=fileService.uploadFiles(multipartFile,"marker","specify_image");
       ARMarkerEntity newARMarkerEntity=ARMarkerEntity.builder()
               .markerImageId(arMarkerRequestDto.getMarkerImageId()).latitude(arMarkerRequestDto.getLatitude()).longitude(arMarkerRequestDto.getLongitude())
               .specifyImg(imagePath).contentType(arMarkerRequestDto.getContentType()).address(arMarkerRequestDto.getAddress())
               .build();
       aRMarkerRepository.insert(newARMarkerEntity);
       int newARMarkerId=newARMarkerEntity.getARMarkerId();
       return newARMarkerId;
    }
    public ARMarkerListDto findById(int id){
        ArrayList<Map.Entry<MarkerImageEntity,ARMarkerEntity>> markerList=new ArrayList<>();
        ARMarkerEntity selectedMarker = aRMarkerRepository.selectById(id);
        MarkerImageEntity markerImageEntity = markerImageRepository.selectById(selectedMarker.getMarkerImageId());

        markerList.add(new AbstractMap.SimpleEntry<>(markerImageEntity, selectedMarker));
        ARMarkerListDto selectedARMarkerListDto = ARMarkerListDto.builder()
                .markerList(markerList)
                .build();
        return selectedARMarkerListDto;
    }

    public int delete(int id){
        int isSuccess=aRMarkerRepository.delete(id);
        return isSuccess;
    }
}

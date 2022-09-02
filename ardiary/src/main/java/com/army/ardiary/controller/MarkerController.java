package com.army.ardiary.controller;

import com.army.ardiary.dto.MarkerImageListDto;
import com.army.ardiary.dto.MarkerListDto;
import com.army.ardiary.service.CameraARService;
import com.army.ardiary.service.MarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/markers")
public class MarkerController {

    private final MarkerService markerService;
    final CameraARService cameraARService;

    @GetMapping("/image")
    public ResponseEntity<?> loadMarkerImageList(){
        MarkerImageListDto markerImageListDto = markerService.findMarkerImageList();
        return ResponseEntity.status(HttpStatus.OK).body(markerImageListDto);
    }

    @GetMapping("")
    public ResponseEntity<?> loadMarkers(){
        MarkerListDto markerListDto = markerService.findMarkerList();
        return ResponseEntity.status(HttpStatus.OK).body(markerListDto);
    }

    //filteriing이라고 생각해서 쿼리 사용
    @GetMapping("/location")
    public ResponseEntity<?> loadCMarkersByLocation(@RequestParam double lat, @RequestParam double lon){
        int defaultDistance=10;
        MarkerListDto markerListDto = markerService.findMarkerListByLocation(lat, lon,defaultDistance);
        return ResponseEntity.status(HttpStatus.OK).body(markerListDto);
    }
}

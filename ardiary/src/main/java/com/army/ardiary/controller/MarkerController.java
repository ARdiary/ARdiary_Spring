package com.army.ardiary.controller;

import com.army.ardiary.dto.MarkerListDto;
import com.army.ardiary.service.MarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MarkerController {

    private final MarkerService markerService;

    @GetMapping("/api/markers")
    public ResponseEntity<?> loadMarkerList(){
        MarkerListDto markerListDto = markerService.findMarkerList();
        return ResponseEntity.status(HttpStatus.OK).body(markerListDto);

    }
}

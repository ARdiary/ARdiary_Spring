package com.army.ardiary.controller;

import com.army.ardiary.dto.CameraARListDto;
import com.army.ardiary.dto.CameraARRequestDto;
import com.army.ardiary.service.CameraARService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cameraAR")
public class CameraARController {
    final CameraARService cameraARService;
    @PostMapping()
    public ResponseEntity<?> loadCameraAR(){
        CameraARListDto cameraARListDto = cameraARService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(cameraARListDto);
    }
/*    @PostMapping("/add")
    ResponseEntity<?> addCameraAR(@RequestBody @Valid CameraARRequestDto cameraARRequestDto){

        return
    }*/


}

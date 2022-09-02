package com.army.ardiary.controller;

import com.army.ardiary.dto.CameraARRequestDto;
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
@RequestMapping("/cameraAR")
public class CameraARController {
    @PostMapping()
    public ResponseEntity<?> loadCameraAR(){

        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseDto);
    }
/*    @PostMapping("/add")
    ResponseEntity<?> addCameraAR(@RequestBody @Valid CameraARRequestDto cameraARRequestDto){

        return
    }*/


}

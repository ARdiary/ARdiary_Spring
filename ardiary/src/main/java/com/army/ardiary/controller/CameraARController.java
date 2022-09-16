package com.army.ardiary.controller;

import com.army.ardiary.dto.CameraARRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cameraAR")
public class CameraARController {

/*    @GetMapping("/distance={distance}")
    public ResponseEntity<?> loadCameraARByDistance(@RequestParam int distance, @RequestBody @Valid MyLocatioinDto myLocatioinDto){
       // CameraARListDto cameraARListDto =cameraARService.findByDistance(myLocatioinDto,distance);
        return ResponseEntity.status(HttpStatus.OK).body(cameraARListDto);
    }*/
/*    @PostMapping("")
    ResponseEntity<?> createCameraAR(@RequestBody @Valid CameraARRequestDto cameraARRequestDto){
        int newCameraARId=cameraARService.createCameraAR(cameraARRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCameraARId);
    }*/


}

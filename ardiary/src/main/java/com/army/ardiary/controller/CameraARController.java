package com.army.ardiary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        int newARMarkerId=cameraARService.createCameraAR(cameraARRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newARMarkerId);
    }*/


}

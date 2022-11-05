package com.army.ardiary.controller;

import com.army.ardiary.dto.*;
import com.army.ardiary.service.MarkerService;
import com.army.ardiary.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/markers")
public class MarkerController {

    private final MarkerService markerService;
    private final TokenService tokenService;

    @GetMapping("/image")
    public ResponseEntity<?> loadMarkerImageList(){
        MarkerImageListDto markerImageListDto = markerService.findMarkerImageList();
        return ResponseEntity.status(HttpStatus.OK).body(markerImageListDto);
    }

    @GetMapping("")
    public ResponseEntity<?> loadMarkers(){
        ARMarkerListDto ARMarkerListDto = markerService.findMarkerList();
        return ResponseEntity.status(HttpStatus.OK).body(ARMarkerListDto);
    }

    //filteriing이라고 생각해서 쿼리 사용
    @GetMapping("/location")
    public ResponseEntity<?> loadMarkersByLocation(@RequestParam double lat, @RequestParam double lon){
        int defaultDistance=10;
        ARMarkerListDto ARMarkerListDto = markerService.findMarkerListByLocation(lat, lon,defaultDistance);
        return ResponseEntity.status(HttpStatus.OK).body(ARMarkerListDto);
    }

    @PostMapping("")
    public ResponseEntity<?> writeARMarker(@RequestHeader(value = "Authorization") String headerToken, ARMarkerRequestDto arMarkerRequestDto){
        String token;
        String prefix = headerToken.substring(0, "Bearer ".length());
        if(prefix == "Bearer "){
            token = headerToken.substring("Bearer ".length());
        }else{
            token = headerToken;
        }
        //토큰 유효성 확인
        if(token==null){ //토큰을 보내지않은 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 요청입니다. 토큰도 함께 보내주세요."));
        }
        if(!tokenService.validateToken(token)){ //토큰이 유효하지않은 경우: 지금 이 에러 발생. 토큰이 변조된 상황.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("사용자 인증이 불가합니다. 잘못된 접근입니다."));
        }

        //토큰을 통해 사용자 정보 불러오기
        int userId = tokenService.findUserIdByJwt(token);
        //AR마커 생성 후, 불러오기
        int newARMarkerId=markerService.createARMarker(arMarkerRequestDto,userId);
        ARMarkerListDto arMarkerListDto=markerService.findById(newARMarkerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(arMarkerListDto);
    }

}

package com.army.ardiary.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@NoArgsConstructor
@RequiredArgsConstructor
public class FileService {

    //기본 루트 주소. 나중에 aws s3 파서 변경할 예정
    String uploadPath="C:\\Users\\prkim\\source\\repos\\ARdiary\\ARdiray_Spring\\ardiary\\src\\main\\resources\\files";
    public String uploadFiles(MultipartFile[] uploadFiles){
        ArrayList<String> filePaths=new ArrayList<String>();
        //파일 비었는지 확인
        if( uploadFiles!=null){
            for(MultipartFile file : uploadFiles){

                String originalName = file.getOriginalFilename();
                //파일 경로 말고 파일 이름만 가져오기
                String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
                //UUID: 범용 고유 식별자
                String uuid = UUID.randomUUID().toString();
                //lastIndexOf("__")앞까지가 uuid임. 그 이후는 서버로 전송된 상태의 파일 이름
                String savefileName = uploadPath + File.separator + uuid + "__" + fileName;

                Path savePath = Paths.get(savefileName);
                try {
                    //multipart 파일을 해당 위치에 저장(write)
                    file.transferTo(savePath);
                    //파일의 저장 위치 리스트에 추가
                    filePaths.add(savePath.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }}
        return filePaths.toString();
    }



}

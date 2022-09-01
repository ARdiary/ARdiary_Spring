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

    String uploadPath="C:\\Users\\prkim\\source\\repos\\ARdiary\\ARdiray_Spring\\ardiary\\src\\main\\resources\\files";
    public String uploadFiles(MultipartFile[] uploadFiles){
        ArrayList<String> filePaths=new ArrayList<String>();
        if( uploadFiles!=null){
        for(MultipartFile file : uploadFiles){

            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            String uuid = UUID.randomUUID().toString();

            String savefileName = uploadPath + File.separator + uuid + "__" + fileName;

            Path savePath = Paths.get(savefileName);
            try {
                file.transferTo(savePath);
                filePaths.add(savePath.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}
        return filePaths.toString();
    }



}

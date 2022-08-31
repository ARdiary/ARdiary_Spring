package com.army.ardiary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    String uploadPath="";
    public void uploadImage(MultipartFile[] uploadFiles){

        for(MultipartFile file : uploadFiles){

            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            String uuid = UUID.randomUUID().toString();

            String savefileName = uploadPath + File.separator + uuid + "__" + fileName;

            Path savePath = Paths.get(savefileName);

            try {
                file.transferTo(savePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

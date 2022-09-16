package com.army.ardiary.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
@RequiredArgsConstructor
public class FileService {

    //s3버킷
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    //기본 루트 주소. 나중에 aws s3 파서 변경할 예정
    public String uploadFiles(MultipartFile[] uploadFiles,String type){
        //type은 해당 파일이 timecapsule,diary, guestbook 중 어느 폴더에 저장될지 결정
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
                String savefileName = type+ "/" + uuid + "__" + fileName;

                 try {
                    ObjectMetadata objMeta = new ObjectMetadata();
                    //파일 사이즈 읽어와 objectmetadata 세팅
                    objMeta.setContentLength(file.getInputStream().available());
                    //파일 s3에 저장
                    amazonS3.putObject(bucket, savefileName, file.getInputStream(), objMeta);
                    //파일의 저장 위치 리스트에 추가
                     filePaths.add(amazonS3.getUrl(bucket,savefileName).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return filePaths.toString();}
        else {
            return null;
        }
    }



}

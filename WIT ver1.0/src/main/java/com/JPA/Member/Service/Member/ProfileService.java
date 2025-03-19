package com.JPA.Member.Service.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;
import com.JPA.Member.config.S3Config;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class ProfileService {

    private final S3Config s3Config;

    @Autowired
    public ProfileService(S3Config s3Config) {
        this.s3Config = s3Config;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String imageUpload(MultipartRequest request) throws IOException {

        // request 인자에서 이미지 파일을 뽑아냄
        MultipartFile file = request.getFile("upload");

        // S3에 바로 업로드하는 메서드 호출
        String s3Url = imageUploadFromFile(file);

        return s3Url;
    }

    public String imageUploadFromFile(MultipartFile file) throws IOException {

        // 파일 이름과 확장자 추출
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));

        // 유일한 파일 이름을 위해 UUID 사용
        String uuidFileName = UUID.randomUUID() + ext;

        // 파일을 InputStream으로 변환
        InputStream inputStream = file.getInputStream();

        // S3에 파일 업로드 (ACL 설정 없이)
        s3Config.amazonS3Client().putObject(
                new PutObjectRequest(bucket, uuidFileName, inputStream, null));

        // S3 URL 생성
        String s3Url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();

        // InputStream 닫기
        inputStream.close();

        return s3Url;
    }

}

package org.oz.association_boot.utill.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@RequiredArgsConstructor
@Log4j2
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Value("${cloud.aws.s3.upload-folder:}")
    private String folderName;

    public String getFolderPath(String s3UploadFolder) {
        if (s3UploadFolder != null && !s3UploadFolder.isEmpty()) {
            return bucket + "/" + folderName + "/" + s3UploadFolder;
        }

        return bucket + "/" + folderName;
    }

    // S3로 파일 업로드하기
    public String upload(String filePath, String s3UploadFolder)throws RuntimeException {

        File targetFile = new File(filePath);
        log.info(targetFile);

        String uploadImageUrl = putS3(targetFile, targetFile.getName(),s3UploadFolder); // s3로 업로드
        removeOriginalFile(targetFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName, String s3UploadFolder)throws RuntimeException {

        amazonS3Client.putObject(new PutObjectRequest(getFolderPath(s3UploadFolder), fileName,
                uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(getFolderPath(s3UploadFolder), fileName).toString();
    }

    // S3 업로드 후 원본 파일 삭제
    private void removeOriginalFile(File targetFile) {
        if (targetFile.exists() && targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("fail to remove");
    }

    // S3 파일 가져오기
    public Resource getFileUrl(String fileName, String s3UploadFolder) {
        String uploadPath = bucket + "/" + getFolderPath(s3UploadFolder);

        // S3 객체 로드
        S3Object s3Object = amazonS3Client.getObject(getFolderPath(s3UploadFolder), fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        // InputStream을 Resource로 변환
        Resource resource = new InputStreamResource(inputStream);

        return resource;
    }

    // S3 버켓 파일 삭제
    public void removeS3File(String fileName, String s3UploadFolder){
        final DeleteObjectRequest deleteObjectRequest = new
                DeleteObjectRequest(getFolderPath(s3UploadFolder), fileName);
        amazonS3Client.deleteObject(deleteObjectRequest);
    }
}
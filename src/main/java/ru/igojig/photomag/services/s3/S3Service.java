package ru.igojig.photomag.services.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.igojig.photomag.configs.S3Properties;
import ru.igojig.photomag.exceptions.UploadException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    public void upload(MultipartFile file, Long eventId, Long imageId) {

        String key=makeKey(eventId, imageId, file);

        try (InputStream is = file.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3Properties.getBucket())
                    .key(key)
                    .build();

            RequestBody requestBody = RequestBody.fromInputStream(is, file.getSize());

            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, requestBody);

            log.info("file uploaded {}", putObjectResponse);


        } catch (IOException | SdkException e) {
            log.error("Error while upload to S3", e);
            UploadException uploadException=new UploadException(e.getMessage());
            uploadException.setEventId(eventId);
            uploadException.setImageId(imageId);
            uploadException.setFileName(file.getOriginalFilename());
            throw uploadException;
        }

    }

    private String makeKey(Long eventId, Long imageId, MultipartFile file){

        return eventId + "/" + imageId + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
    }

}

package ru.igojig.photomag.controllers.images;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.igojig.photomag.entities.Image;
import ru.igojig.photomag.exceptions.UploadException;
import ru.igojig.photomag.response.ImageUploadErrorResponse;
import ru.igojig.photomag.response.ImageUploadResponse;
import ru.igojig.photomag.services.ImageDataService;
import ru.igojig.photomag.services.s3.S3Service;

@Slf4j
@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class UploadController {

    private final ImageDataService imageDataService;
    //
    private final S3Service s3Service;


    @Transactional
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<ImageUploadResponse> upload(@RequestParam("multipartFile") MultipartFile file, @RequestParam("eventId") Long eventId) {


        Image image = imageDataService.create(file, eventId);

//        s3Service.upload(file, eventId, image.getId());


        log.info("file: {} upload", file.getOriginalFilename());

        return new ResponseEntity<>(ImageUploadResponse.builder()
                .imageId(image.getId())
                .fileName(image.getFileName())
                .build(),
                HttpStatus.OK);
    }

    @HxRequest
    @GetMapping("/uploadForm")
    public String uploadForm() {

        return "fragments/images/uploadForm::uploadForm";

    }

    @ExceptionHandler(UploadException.class)
    @ResponseBody
    public ResponseEntity<ImageUploadErrorResponse> uploadError(UploadException e) {
        log.error("Upload error handler", e);

        return new ResponseEntity<>(ImageUploadErrorResponse.builder()
                .imageId(e.getImageId())
                .fileName(e.getFileName())
                .eventId(e.getEventId())
                .message(e.getMessage())
                .build(), HttpStatusCode.valueOf(600));
    }

//    @ExceptionHandler(EventNotFoundException.class)
//    @ResponseBody
//    public ResponseEntity<?> uploadError1(EventNotFoundException e) {
//        log.error("Upload image error", e);
//        return new ResponseEntity<>(ImageUploadErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.NOT_FOUND);
//    }

}

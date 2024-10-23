package ru.igojig.photomag.controllers.images;

import com.dropbox.core.v2.files.FileMetadata;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.igojig.photomag.components.ImagesUploadInfo;
import ru.igojig.photomag.entities.Image;
import ru.igojig.photomag.exceptions.EventException;
import ru.igojig.photomag.exceptions.SaveToDropBoxException;
import ru.igojig.photomag.response.ImageErrorResponse;
import ru.igojig.photomag.response.ImageSaveResponse;
import ru.igojig.photomag.services.ImageDataService;
import ru.igojig.photomag.services.dropbox.DropBoxService;

@Slf4j
@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class UploadController {

    private final ImageDataService imageDataService;
    private final DropBoxService dropBoxService;

    private final ImagesUploadInfo imagesUploadInfo;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<ImageSaveResponse> upload(@RequestParam("multipartFile") MultipartFile file, @RequestParam("eventId") Long eventId) {




        Image image = imageDataService.create(file, eventId);

        FileMetadata save = dropBoxService.save(file, image);

//                if (Random) {
//            throw new SaveToDropBoxException("Test");
//        }

        System.out.printf("eventId[%s] name[%s] size[%s] origin[%s]%n", eventId, file.getName(), file.getSize(), file.getOriginalFilename());


        return new ResponseEntity<>(ImageSaveResponse.builder()
                .imageId(image.getId())
                .fileName(image.getFileName())
                .build(),
                HttpStatus.OK);
    }

    @HxRequest
    @GetMapping("/uploadForm")
    public String uploadForm() {
        imagesUploadInfo.getExceptionList().clear();
        return "fragments/images/uploadForm::uploadForm";

    }

    @ExceptionHandler(SaveToDropBoxException.class)
    @ResponseBody
    public ResponseEntity<ImageErrorResponse> uploadError(SaveToDropBoxException e) {
        log.error("Upload image error", e);

        imagesUploadInfo.getExceptionList().add(e);

        return new ResponseEntity<>(ImageErrorResponse.builder()
                .imageId(e.getImageId())
                .fileName(e.getFileName())
                .message(e.getMessage())
                .build(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(EventException.class)
    @ResponseBody
    public ResponseEntity<?> uploadError1(EventException e){
        log.error("Upload image error", e);
        imagesUploadInfo.getExceptionList().add(SaveToDropBoxException.builder().message(e.getMessage()).build());
        return new ResponseEntity<>(ImageErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

}

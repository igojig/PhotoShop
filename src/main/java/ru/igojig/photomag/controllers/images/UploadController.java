package ru.igojig.photomag.controllers.images;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.igojig.photomag.services.ImageDataService;

@Slf4j
@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class UploadController {

    private final ImageDataService imageDataService;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("multipartFile") MultipartFile file, @RequestParam("eventId") Long eventId) {

        imageDataService.create(file, eventId);

        System.out.printf("eventId[%s] name[%s] size[%s] origin[%s]%n", eventId, file.getName(), file.getSize(), file.getOriginalFilename());

        return "";
    }

    @HxRequest
    @GetMapping("/uploadForm")
    public String uploadForm(){
        return "fragments/images/uploadForm::uploadForm";
    }

}

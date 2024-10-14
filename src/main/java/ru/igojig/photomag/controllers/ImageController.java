package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.igojig.photomag.entities.ImageData;
import ru.igojig.photomag.mappers.EventMapper;
import ru.igojig.photomag.mappers.ImageMapper;
import ru.igojig.photomag.model.EventTableModel;
import ru.igojig.photomag.model.ImageShowModel;
import ru.igojig.photomag.services.ImageDataService;
import ru.igojig.photomag.services.event.EventService;
import ru.igojig.photomag.services.image.ImageService;
import ru.igojig.photomag.utils.ImageUtils;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
//@CrossOrigin(value = "http://localhost:4200")
public class ImageController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final ImageDataService imageDataService;
    private final ImageUtils imageUtils;




    @HxRequest
    @GetMapping("/{eventId}")
    public String photo(@PathVariable("eventId") Long eventId, Model model) {
        EventTableModel eventTableModel = eventMapper.toTableModel(eventService.findById(eventId));
        List<ImageShowModel> imageShowModels = imageService.findAllByEventId(eventId).stream().map(imageMapper::toShowModel).toList();

        model.addAttribute("eventTableModel", eventTableModel);
        model.addAttribute("imageShowModels", imageShowModels);
        return "/fragments/images/photo:: photo";
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public Resource downloadImage(@PathVariable("id") Long imageDataId) {

        ImageData imageData = imageDataService.findById(imageDataId);

        return new ByteArrayResource(imageData.getBase64Content());
    }

//    @GetMapping("/{id}")
//    public void showProductImage(@PathVariable Long id,
//                                 HttpServletResponse response) throws IOException {
//        response.setContentType("image/jpeg"); // Or whatever format you wanna use
//
//        ImageData imageData=  imageDataService.findById(id);
//
//        InputStream is = new ByteArrayInputStream(imageData.getBase64Content());
//        IOUtils.copy(is, response.getOutputStream());
//    }


    @PostMapping("/upload")
    @ResponseBody
    public String getFiles(@RequestParam("multipartFile") MultipartFile file, @RequestParam("eventId") Long eventId) {

        imageDataService.create(file, eventId);

        System.out.printf("eventId[%s] name[%s] size[%s] origin[%s]%n", eventId, file.getName(), file.getSize(), file.getOriginalFilename());

        return "";
    }

}

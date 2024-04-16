package ru.igojig.photomag.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.converters.ImageConverter;
import ru.igojig.photomag.dtos.ImageDto;
import ru.igojig.photomag.entities.Image;
import ru.igojig.photomag.services.image.ImageService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class ImageController {

    private final ImageService imageService;
    private final ImageConverter imageConverter;

    @GetMapping("/images")
    public List<ImageDto> findAll(){
        return imageService.findAll().stream()
                .map(imageConverter::toDto)
                .toList();
    }

    @GetMapping("/events/{id}/images")
    public List<ImageDto> findAllByEventId(@PathVariable("id") Long id){
        return imageService.findAllByEventId(id).stream()
                .map(imageConverter::toDto)
                .toList();
    }

    @GetMapping("/images/{id}")
    public ImageDto findById(@PathVariable("id") Long id){
//        return imageConverter.entityToDto(imageService.findById(id));
        return imageConverter.toDto(imageService.findById(id));
    }

    @PostMapping("/events/{id}/images")
    public ImageDto create(@PathVariable("id") Long id,  @RequestBody ImageDto imageDto){
        return imageConverter.toDto(imageService.create(id, imageConverter.toEntity(imageDto)));
    }

}

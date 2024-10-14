package ru.igojig.photomag.services;

import org.springframework.web.multipart.MultipartFile;
import ru.igojig.photomag.entities.ImageData;


public interface ImageDataService {

    ImageData findById(Long id);

    void save(ImageData imageData);

    void create(MultipartFile file, Long eventId);
}

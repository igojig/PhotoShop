package ru.igojig.photomag.services.image;

import ru.igojig.photomag.dtos.ImageDto;
import ru.igojig.photomag.entities.Image;

import java.util.List;

public interface ImageService {
    List<Image> findAll();

    Image create(Long id, Image image);

    List<Image> findAllByEventId(Long id);

    Image findById(Long id);
}

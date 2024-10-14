package ru.igojig.photomag.services.image;

import ru.igojig.photomag.entities.Image;

import java.util.List;

public interface ImageService {
//    List<Image> findAll();

//    Image create(Long id, Image image);

    List<Image> findAllByEventId(Long id);

    void save(Image image);

//    Image findById(Long id);
}

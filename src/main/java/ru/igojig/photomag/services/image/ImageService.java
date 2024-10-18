package ru.igojig.photomag.services.image;

import ru.igojig.photomag.entities.Image;
import ru.igojig.photomag.model.ImageUpdateModel;

import java.util.List;

public interface ImageService {
//    List<Image> findAll();

//    Image create(Long id, Image image);

    List<Image> findAllByEventId(Long id);

    List<Image> findAllByEventIdOrderByTimeAsc(Long id);

    void save(Image image);

    void updateBatch(List<ImageUpdateModel> imageUpdateModels);

//    Image findById(Long id);
}

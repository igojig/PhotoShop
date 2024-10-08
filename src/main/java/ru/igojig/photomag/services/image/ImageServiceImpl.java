package ru.igojig.photomag.services.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.entities.Image;
import ru.igojig.photomag.repositories.ImageRepository;
import ru.igojig.photomag.services.event.EventService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final EventService eventService;

    @Override
//    @Transactional
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    @Transactional
    public Image create(Long id, Image image) {
        Event event=eventService.findById(id);
        Image newImage=new Image();
        newImage.setFileName(image.getFileName());
        newImage.setFilePath(image.getFilePath());
        newImage.setEvent(event);
        newImage=imageRepository.save(newImage);
        return newImage;
    }

    @Override
    public List<Image> findAllByEventId(Long id) {
        return imageRepository.findAllByEventId(id);
    }

    @Override
//    @Transactional
    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow();
    }
}

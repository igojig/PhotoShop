package ru.igojig.photomag.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.entities.Image;
import ru.igojig.photomag.entities.ImageData;
import ru.igojig.photomag.repositories.ImageDataRepository;
import ru.igojig.photomag.services.event.EventService;
import ru.igojig.photomag.utils.ImageUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ImageDataServiceImpl implements ImageDataService {
    private final ImageDataRepository imageDataRepository;
    private final EventService eventService;
    private final ImageUtils imageUtils;


    @Override
    public ImageData findById(Long id) {
        return imageDataRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public void save(ImageData imageData) {
        imageDataRepository.save(imageData);
    }


    @Override
    public Image create(MultipartFile file, Long eventId) {
        Event event = eventService.getReferenceById(eventId);

        LocalDateTime dateTime = imageUtils.extractDateTime(file);

        byte[] resizedImg = imageUtils.resize(file);

        Image image = new Image();
        image.setEvent(event);
        image.setFileName(StringUtils.getFilename(file.getOriginalFilename()));
        image.setFilePath(String.valueOf(eventId));
        image.setDateTime(dateTime);
        image.setPerformanceNumber(-1L);

        ImageData imageData = new ImageData();
        imageData.setBase64Content(resizedImg);
        imageData.setImage(image);

       imageDataRepository.save(imageData);

        return image;

    }

}

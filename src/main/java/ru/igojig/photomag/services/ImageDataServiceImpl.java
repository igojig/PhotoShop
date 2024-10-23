package ru.igojig.photomag.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.entities.Image;
import ru.igojig.photomag.entities.ImageData;
import ru.igojig.photomag.repositories.ImageDataRepository;
import ru.igojig.photomag.services.dropbox.DropBoxService;
import ru.igojig.photomag.services.event.EventService;
import ru.igojig.photomag.utils.ImageUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ImageDataServiceImpl implements ImageDataService {
    private final ImageDataRepository imageDataRepository;
    private final EventService eventService;
    private final ImageUtils imageUtils;
    private final DropBoxService dropBoxService;

    @Override
    public ImageData findById(Long id) {
        return imageDataRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public void save(ImageData imageData) {
        imageDataRepository.save(imageData);
    }

    @Transactional
    @Override
    public Image create(MultipartFile file, Long eventId) {
        Event event = eventService.findById(eventId);

        LocalDateTime dateTime = imageUtils.extractDateTime(file);

        byte[] resizedImg = imageUtils.resize(file);

        Image image = new Image();
        image.setEvent(event);
        image.setFileName(file.getOriginalFilename());
        image.setFilePath(makePath(event));
        image.setDateTime(dateTime);
        image.setPerformanceNumber(-1L);

        ImageData imageData = new ImageData();
        imageData.setBase64Content(resizedImg);
        imageData.setImage(image);

       imageDataRepository.save(imageData);

        return image;

    }

    private String makePath(Event event) {
        return String.format("/%s/%s/%s/%s", event.getFestival().getId(), event.getRoom().getHall().getId(), event.getRoom().getId(), event.getId());
    }
}

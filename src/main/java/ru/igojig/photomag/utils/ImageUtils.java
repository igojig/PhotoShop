package ru.igojig.photomag.utils;

import org.springframework.web.multipart.MultipartFile;
import ru.igojig.photomag.model.ImageUpdateModel;

import java.time.LocalDateTime;
import java.util.List;

public interface ImageUtils {
    byte[] resize(MultipartFile file);

    LocalDateTime extractDateTime(MultipartFile file);

    void generatePerformances(List<ImageUpdateModel> imageUpdateModels);
}

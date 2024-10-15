package ru.igojig.photomag.utils;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public interface ImageUtils {
    byte[] resize(MultipartFile file);

    LocalDateTime extractDateTime(MultipartFile file);
}

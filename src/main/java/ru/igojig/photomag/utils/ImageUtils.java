package ru.igojig.photomag.utils;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUtils {
    byte[] resize(MultipartFile file);
}

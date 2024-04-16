package ru.igojig.photomag.converters;

import ru.igojig.photomag.dtos.ImageDto;
import ru.igojig.photomag.entities.Image;

public interface ImageConverter {
     ImageDto toDto(Image image);
     Image toEntity(ImageDto imageDto);
}

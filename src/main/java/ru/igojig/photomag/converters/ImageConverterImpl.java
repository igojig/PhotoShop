package ru.igojig.photomag.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.igojig.photomag.dtos.ImageDto;
import ru.igojig.photomag.entities.Image;

@Component
@RequiredArgsConstructor
public class ImageConverterImpl implements ImageConverter {
    private final EventConverter eventConverter;
    public ImageDto toDto(Image image){
        ImageDto imageDto =new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setFileName(image.getFileName());
        imageDto.setFilePath(image.getFilePath());
        imageDto.setEventDto(eventConverter.entityToDto(image.getEvent()));
        return imageDto;
    }

    @Override
    public Image toEntity(ImageDto imageDto) {
        Image image=new Image();
        image.setId(imageDto.getId());
        image.setFileName(imageDto.getFileName());
        image.setFilePath(imageDto.getFilePath());
        image.setEvent(eventConverter.dtoToEntity(imageDto.getEventDto()));
        return image;
    }
}

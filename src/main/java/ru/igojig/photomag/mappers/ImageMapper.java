package ru.igojig.photomag.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.igojig.photomag.entities.Image;
import ru.igojig.photomag.model.ImageShowModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {

    ImageShowModel toShowModel(Image image);
    Image toEntity(ImageShowModel imageShowModel);



}

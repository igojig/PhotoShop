package ru.igojig.photomag.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.igojig.photomag.entities.Image;
import ru.igojig.photomag.model.ImageShowModel;
import ru.igojig.photomag.model.ImageUpdateModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ImageMapper {

   public abstract ImageShowModel toShowModel(Image image);

   @Mapping(target = "eventId", source = "event.id")
   public abstract ImageUpdateModel toUpdateModel(Image image);
   public abstract Image toEntity(ImageShowModel imageShowModel);



}

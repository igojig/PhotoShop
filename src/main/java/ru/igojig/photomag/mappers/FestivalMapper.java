package ru.igojig.photomag.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.igojig.photomag.entities.Festival;
import ru.igojig.photomag.model.FestivalModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FestivalMapper {
    FestivalModel toModel(Festival festival);
    Festival toFestival(FestivalModel festivalModel);
}

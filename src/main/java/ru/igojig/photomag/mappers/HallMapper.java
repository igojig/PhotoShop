package ru.igojig.photomag.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.igojig.photomag.entities.Hall;
import ru.igojig.photomag.model.HallModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)

public interface HallMapper {
    HallModel toModel(Hall hall);
    Hall toHall(HallModel hallModel);
}

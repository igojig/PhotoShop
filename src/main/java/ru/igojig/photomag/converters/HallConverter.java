package ru.igojig.photomag.converters;

import ru.igojig.photomag.dtos.HallDto;
import ru.igojig.photomag.entities.Hall;

public interface HallConverter {
    HallDto entityToDto(Hall hall);
    Hall dtoToEntity(HallDto hallDto);
}

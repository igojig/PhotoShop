package ru.igojig.photomag.converters;

import ru.igojig.photomag.dtos.FestivalDto;
import ru.igojig.photomag.entities.Festival;

public interface FestivalConverter {
    Festival dtoToEntity(FestivalDto festivalDto);
    FestivalDto entityToDto(Festival festival);
}

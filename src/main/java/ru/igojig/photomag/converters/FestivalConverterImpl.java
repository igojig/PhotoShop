package ru.igojig.photomag.converters;

import org.springframework.stereotype.Component;
import ru.igojig.photomag.dtos.FestivalDto;
import ru.igojig.photomag.entities.Festival;

@Component
public class FestivalConverterImpl implements FestivalConverter{
    public Festival dtoToEntity(FestivalDto dto){
        Festival festival=new Festival();
        festival.setId(dto.getId());
        festival.setName(dto.getName());
        return festival;
    }

    public FestivalDto entityToDto(Festival festival){
        FestivalDto dto=new FestivalDto();
        dto.setId(festival.getId());
        dto.setName(festival.getName());
        return dto;
    }
}

package ru.igojig.photomag.converters;

import org.springframework.stereotype.Component;
import ru.igojig.photomag.dtos.HallDto;
import ru.igojig.photomag.entities.Hall;

@Component
public class HallConverterImpl implements HallConverter{

    public HallDto entityToDto(Hall hall){
        HallDto hallDto=new HallDto();
        hallDto.setId(hall.getId());
        hallDto.setName(hall.getName());
        hallDto.setAddress(hall.getAddress());
        return hallDto;
    }

    public Hall dtoToEntity(HallDto hallDto){
        Hall hall=new Hall();
        hall.setId(hallDto.getId());
        hall.setName(hallDto.getName());
        hall.setAddress(hallDto.getAddress());
        return hall;
    }

}

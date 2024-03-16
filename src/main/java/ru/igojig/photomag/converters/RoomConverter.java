package ru.igojig.photomag.converters;

import ru.igojig.photomag.dtos.RoomDto;
import ru.igojig.photomag.entities.Room;

public interface RoomConverter {

    RoomDto entityToDto(Room room);

    Room dtoToEntity(RoomDto roomDto);
}

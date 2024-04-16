package ru.igojig.photomag.converters.room;

import ru.igojig.photomag.dtos.rooms.RoomDto;
import ru.igojig.photomag.entities.Room;

public interface RoomConverter {

    RoomDto toDto(Room room);


    Room toEntity(RoomDto roomDto);

}

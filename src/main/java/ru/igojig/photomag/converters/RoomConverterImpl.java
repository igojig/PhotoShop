package ru.igojig.photomag.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.igojig.photomag.dtos.RoomDto;
import ru.igojig.photomag.entities.Room;

@Component
@RequiredArgsConstructor
public class RoomConverterImpl implements RoomConverter {
    private final HallConverter hallConverter;

    public RoomDto entityToDto(Room room){
        RoomDto roomDto=new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setHallDto(hallConverter.entityToDto(room.getHall()));
        return roomDto;
    }

    public Room dtoToEntity(RoomDto roomDto){
        Room room=new Room();
        room.setId(roomDto.getId());
        room.setName(roomDto.getName());
        room.setHall(hallConverter.dtoToEntity(roomDto.getHallDto()));

        return room;
    }

}

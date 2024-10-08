package ru.igojig.photomag.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.igojig.photomag.entities.Room;
import ru.igojig.photomag.model.RoomModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {

    public RoomModel toModel(Room room);
    public Room toRoom(RoomModel roomModel);

}

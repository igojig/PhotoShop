package ru.igojig.photomag.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.igojig.photomag.entities.Room;
import ru.igojig.photomag.model.RoomModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {
//    @Mapping(target = "roomId", source = "id")
    public RoomModel toModel(Room room);

//    @Mapping(target = "id", source = "roomId")
    public Room toEntity(RoomModel roomModel);

}

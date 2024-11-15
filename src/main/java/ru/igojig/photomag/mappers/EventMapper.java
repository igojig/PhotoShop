package ru.igojig.photomag.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.model.EventEditModel;
import ru.igojig.photomag.model.EventFormModel;
import ru.igojig.photomag.model.EventTableModel;
import ru.igojig.photomag.services.Room.RoomService;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EventMapper {

    @Autowired
    protected RoomService roomService;

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "hallId", source = "room.hall.id")
    public abstract EventEditModel toEditModel(Event event);

    @Mapping(target = "hallName", source = "room.hall.name")
    @Mapping(target = "roomName", source = "room.name")
    public abstract EventTableModel toTableModel(Event event);

    @Mapping(target = "room", expression = "java(roomService.findById(eventEditModel.getRoomId()))")
    public abstract Event toEntity(EventEditModel eventEditModel);

    @Mapping(target = "text", source = "name")
    public abstract EventFormModel toFormModel(Event event);
}

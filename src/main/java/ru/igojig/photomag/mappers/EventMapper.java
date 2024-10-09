package ru.igojig.photomag.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.model.EventEditModel;
import ru.igojig.photomag.model.EventTableModel;
import ru.igojig.photomag.services.Room.RoomService;
import ru.igojig.photomag.services.festival.FestivalService;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EventMapper {

    @Autowired
    protected FestivalService festivalService;

    @Autowired
    protected RoomService roomService;

    @Mapping(target = "festivalId", source = "festival.id")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "hallId", source = "room.hall.id")
    public abstract EventEditModel toEditModel(Event event);

    @Mapping(target = "festivalName", source = "festival.name")
    @Mapping(target = "hallName", source = "room.hall.name")
    @Mapping(target = "roomName", source = "room.name")
    public abstract EventTableModel toTableModel(Event event);

    @Mapping(target = "festival", expression = "java(festivalService.findById(eventEditModel.getFestivalId()))" )
    @Mapping(target = "room", expression = "java(roomService.findById(eventEditModel.getRoomId()))")
    public abstract Event toEntity(EventEditModel eventEditModel);
}

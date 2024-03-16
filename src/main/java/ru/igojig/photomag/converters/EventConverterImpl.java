package ru.igojig.photomag.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.igojig.photomag.dtos.EventDto;
import ru.igojig.photomag.entities.Event;

@Component
@RequiredArgsConstructor
public class EventConverterImpl implements EventConverter {

    private final FestivalConverter festivalConverter;
    private final RoomConverter roomConverter;

    public EventDto entityToDto(Event event){
        EventDto eventDto=new EventDto();

        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setStartDate(event.getStartDate());
        eventDto.setFestivalDto(festivalConverter.entityToDto(event.getFestival()));
        eventDto.setRoomDto(roomConverter.entityToDto(event.getRoom()));

        return eventDto;
    }

    public Event dtoToEntity(EventDto eventDto){
        Event event=new Event();

        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setStartDate(eventDto.getStartDate());
        event.setFestival(festivalConverter.dtoToEntity(eventDto.getFestivalDto()));
        event.setRoom(roomConverter.dtoToEntity(eventDto.getRoomDto()));

        return event;
    }
}

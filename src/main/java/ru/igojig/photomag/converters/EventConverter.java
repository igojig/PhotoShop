package ru.igojig.photomag.converters;

import ru.igojig.photomag.dtos.EventDto;
import ru.igojig.photomag.entities.Event;

public interface EventConverter {
    EventDto entityToDto(Event event);

    Event dtoToEntity(EventDto eventDto);
}

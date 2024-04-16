package ru.igojig.photomag.converters;

import org.springframework.data.domain.Page;
import ru.igojig.photomag.dtos.events.EventDto;
import ru.igojig.photomag.dtos.events.PagebleEventDto;
import ru.igojig.photomag.entities.Event;

public interface EventConverter {
    EventDto entityToDto(Event event);

    Event dtoToEntity(EventDto eventDto);

    PagebleEventDto pageableEntityToDto(Page<Event> pageEvent);
}

package ru.igojig.photomag.converters;

import org.springframework.data.domain.Page;
import ru.igojig.photomag.dtos.EventDto;
import ru.igojig.photomag.dtos.PagebleEventDto;
import ru.igojig.photomag.entities.Event;

public interface EventConverter {
    EventDto entityToDto(Event event);

    Event dtoToEntity(EventDto eventDto);

    PagebleEventDto pageableEntityToDto(Page<Event> pageEvent);
}

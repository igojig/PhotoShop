package ru.igojig.photomag.services.event;

import ru.igojig.photomag.dtos.EventDto;
import ru.igojig.photomag.entities.Event;

import java.util.List;

public interface EventService {
    List<Event> findAll();

    Event findById(Long id);

    Event create(Event event);

    Event update(Long id, Event event);

    void deleteById(Long id);
}

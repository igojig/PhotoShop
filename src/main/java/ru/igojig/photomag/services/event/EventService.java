package ru.igojig.photomag.services.event;

import ru.igojig.photomag.entities.Event;

import java.util.List;

public interface EventService {
    List<Event> findAll();

    Event findById(Long id);

    Event create(Long festId, Long roomId, Event event);

    Event update(Long id, Long festId, Long roomId,  Event event);

    void deleteById(Long id);
}

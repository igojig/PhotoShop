package ru.igojig.photomag.services.event;

import org.springframework.stereotype.Service;
import ru.igojig.photomag.entities.Event;

import java.util.List;

@Service
public interface EventService {
    List<Event> findAll();

    Event findById(Long id);

    Event create(Event event);

    Event update(Event event);

    void deleteById(Long id);

    Event getReferenceById(Long id);

    Long getCount();

    List<Event> findAllOld();
}

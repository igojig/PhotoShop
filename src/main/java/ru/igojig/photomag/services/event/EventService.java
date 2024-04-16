package ru.igojig.photomag.services.event;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.igojig.photomag.entities.Event;

import java.util.List;

@Service
public interface EventService {
    Page<Event> findAll(Integer currentPage, Integer recordsPerPage);

    Event findById(Long id);

    Event create(Event event);

    Event update(Long id, Event event);

    void deleteById(Long id);

    Long getCount();

    List<Event> findAllOld();
}

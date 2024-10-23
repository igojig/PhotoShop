package ru.igojig.photomag.services.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.exceptions.EventException;
import ru.igojig.photomag.repositories.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(()->new EventException("Event not found: " + id));
    }

    public Event getReferenceById(Long id){
//        return eventRepository.findByIdWithoutDetails(id).orElseThrow();
        return eventRepository.getReferenceById(id);
    }

    @Override
    @Transactional
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event update(Event event) {
        Event updEvent=eventRepository.findById(event.getId()).orElseThrow();
        updEvent.setName(event.getName());
        updEvent.setStartDate(event.getStartDate());
        updEvent.setRoom(event.getRoom());
        updEvent.setFestival(event.getFestival());
        return updEvent;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Long getCount() {
        return eventRepository.count();
    }

    @Override
    public List<Event> findAllOld() {
        return eventRepository.findAll();
    }
}

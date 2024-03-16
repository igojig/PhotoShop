package ru.igojig.photomag.services.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.entities.Festival;
import ru.igojig.photomag.entities.Room;
import ru.igojig.photomag.repositories.EventRepository;
import ru.igojig.photomag.repositories.RoomRepository;
import ru.igojig.photomag.services.Room.RoomService;
import ru.igojig.photomag.services.festival.FestivalService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final RoomService roomService;
    private final FestivalService festivalService;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Event create(Long festId, Long roomId, Event event) {
        Festival fest=festivalService.findById(festId);
        Room room=roomService.findById(roomId);
        event.setRoom(room);
        event.setFestival(fest);
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event update(Long id, Long festId, Long roomId, Event event) {
        Event updEvent=eventRepository.findById(id).orElseThrow();
        Festival festival=festivalService.findById(festId);
        Room room=roomService.findById(roomId);
        updEvent.setName(event.getName());
        updEvent.setStartDate(event.getStartDate());
        updEvent.setFestival(festival);
        updEvent.setRoom(room);
        return updEvent;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }
}

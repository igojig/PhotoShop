package ru.igojig.photomag.services.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final RoomService roomService;
    private final FestivalService festivalService;

    @Override
    public Page<Event> findAll(Integer currentPage, Integer recordsPerPage) {
        return eventRepository.findAll(PageRequest.of(currentPage, recordsPerPage));
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Event create(Event event) {
//        Festival fest=festivalService.findById(festId);
//        Room room=roomService.findById(roomId);
//        event.setRoom(room);
//        event.setFestival(fest);
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event update(Long id,  Event event) {
        log.info("Before update");
        Event updEvent=eventRepository.findById(id).orElseThrow();
//        Festival festival=festivalService.findById(event.getFestival().getId());
//        Room room=roomService.findById(event.getRoom().getId());
//        updEvent.setName(event.getName());
//        updEvent.setStartDate(event.getStartDate());
//        updEvent.setFestival(festival);
//        updEvent.setRoom(room);
        updEvent.setName(event.getName());
        updEvent.setStartDate(event.getStartDate());
        updEvent.setRoom(event.getRoom());
        updEvent.setFestival(event.getFestival());
        log.info("after update");

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
}

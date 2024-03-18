package ru.igojig.photomag.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.converters.EventConverter;
import ru.igojig.photomag.dtos.EventDto;
import ru.igojig.photomag.dtos.PagebleEventDto;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.services.event.EventService;

import javax.naming.event.EventDirContext;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
@Slf4j
public class EventController {

    private final EventService eventService;
    private final EventConverter eventConverter;

    @GetMapping("/events")
    public PagebleEventDto findAll(@RequestParam("currentPage") Integer currentPage, @RequestParam("recordsPerPage") Integer recordsPerPage) {
        return eventConverter.pageableEntityToDto(eventService.findAll(currentPage, recordsPerPage));

    }

    @GetMapping("/events/{id}")
    public EventDto findById(@PathVariable("id") Long id) {
//        System.out.println(id);
        return eventConverter.entityToDto(eventService.findById(id));
    }


//    @PostMapping("/events/festivals/{festId}/rooms/{roomId}")
    @PostMapping("/events")
    public EventDto create(@RequestBody EventDto eventDto) {
        return eventConverter.entityToDto(eventService.create(eventConverter.dtoToEntity(eventDto)));
    }

    @PutMapping("/events/{id}")
    public EventDto update(@PathVariable("id") Long id, @RequestBody EventDto eventDto) {
        log.info("Controller start:" + eventDto);
        Event event=eventConverter.dtoToEntity(eventDto);
        event = eventService.update(id, event);
        log.info("Controller end:" + event);
        return eventConverter.entityToDto(event);
    }

    @DeleteMapping("/events/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        eventService.deleteById(id);
    }

    @GetMapping("/events/count")
    public Long getCount(){
        return eventService.getCount();
    }


}

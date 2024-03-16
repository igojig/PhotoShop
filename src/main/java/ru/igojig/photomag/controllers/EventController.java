package ru.igojig.photomag.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.services.event.EventService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public List<Event> findAll() {
        return eventService.findAll();
    }

    @GetMapping("/events/{id}")
    public Event findById(@PathVariable("id") Long id) {
        System.out.println(id);
        return eventService.findById(id);
    }

    @PostMapping("/events/festivals/{festId}/rooms/{roomId}")
    public Event create(@PathVariable("festId") Long festId, @PathVariable("roomId") Long roomId, @RequestBody Event event) {
        return eventService.create(festId, roomId, event);
    }

    @PutMapping("/events/{id}/festivals/{festId}/rooms/{roomId}")
    public Event update(@PathVariable("id") Long id, @PathVariable("festId") Long festId,
                        @PathVariable("roomId") Long roomId, @RequestBody Event event) {
        return eventService.update(id, festId, roomId, event);
    }

    @DeleteMapping("/events/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        eventService.deleteById(id);
    }


}

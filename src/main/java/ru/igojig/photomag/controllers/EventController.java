package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.converters.EventConverter;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.entities.Festival;
import ru.igojig.photomag.entities.Hall;
import ru.igojig.photomag.entities.Room;
import ru.igojig.photomag.services.Hall.HallService;
import ru.igojig.photomag.services.Room.RoomService;
import ru.igojig.photomag.services.event.EventService;
import ru.igojig.photomag.services.festival.FestivalService;

import java.util.List;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
@Slf4j
public class EventController {

    private final EventService eventService;
    private final FestivalService festivalService;
    private final HallService hallService;
    private final RoomService roomService;
    private final EventConverter eventConverter;

    @GetMapping()
    public String findAll(Model model) {
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        return "/events";
    }

    @HxRequest
    @GetMapping
    public String eventTable(Model model) {
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        return "/fragments/events/eventTable:: eventTable";
    }

    @HxRequest
    @GetMapping("/editForm/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Event event = eventService.findById(id);
        List<Festival> festivals = festivalService.findAll();
        List<Hall> halls=hallService.findAll();
        List<Room> rooms=roomService.findAllByHallId(event.getRoom().getHall().getId());

        model.addAttribute("event", event);
        model.addAttribute("festivals", festivals);
        model.addAttribute("halls", halls);
        model.addAttribute("rooms", rooms);

        return "/fragments/events/editEvent::editEvent";
    }

    @HxRequest
    @GetMapping("/getRoomsByHallId")
    public String getRooms(@RequestParam("hallId") Long hallId, @RequestParam(name = "selectedRoomId", required = false) Long selectedRoomId, Model model){
        List<Room> rooms = roomService.findAllByHallId(hallId);
        model.addAttribute("rooms", rooms);
        model.addAttribute("selectedRoomId",selectedRoomId);
        return "/fragments/roomSelectView:: roomSelectView";
    }

    @HxRequest
    @GetMapping("/getFestivals")
    public String getFestivals(@RequestParam(name = "selectedFestivalId", required = false) Long selectedFestivalId, Model model){
        List<Festival> festivals = festivalService.findAll();
        model.addAttribute("festivals", festivals);
        model.addAttribute("selectedFestivalId", selectedFestivalId);

        return "/fragments/festivalSelectView::festivalSelectView";
    }

    @HxRequest
    @GetMapping("/getHalls")
    public String getHalls(@RequestParam(name = "selectedHallId", required = false) Long selectedHallId, Model model){
        List<Hall> halls = hallService.findAll();
        model.addAttribute("halls", halls);
        model.addAttribute("selectedHallId", selectedHallId);

        return "/fragments/hallSelectView::hallSelectView";
    }

//    @GetMapping("/events/old")
//    public List<EventDto> findAll() {
//        return eventService.findAllOld().stream()
//                .map(eventConverter::entityToDto)
//                .toList();
//
//    }
//
//    @GetMapping("/events/{id}")
//    public EventDto findById(@PathVariable("id") Long id) {
////        System.out.println(id);
//        return eventConverter.entityToDto(eventService.findById(id));
//    }
//
//
//    //    @PostMapping("/events/festivals/{festId}/rooms/{roomId}")
//    @PostMapping("/events")
//    public EventDto create(@RequestBody EventDto eventDto) {
//        return eventConverter.entityToDto(eventService.create(eventConverter.dtoToEntity(eventDto)));
//    }
//
//    @PutMapping("/events/{id}")
//    public EventDto update(@PathVariable("id") Long id, @RequestBody EventDto eventDto) {
//        log.info("Controller start:" + eventDto);
//        Event event = eventConverter.dtoToEntity(eventDto);
//        event = eventService.update(id, event);
//        log.info("Controller end:" + event);
//        return eventConverter.entityToDto(event);
//    }
//
//    @DeleteMapping("/events/{id}")
//    public void deleteById(@PathVariable("id") Long id) {
//        eventService.deleteById(id);
//    }
//
//    @GetMapping("/events/count")
//    public Long getCount() {
//        return eventService.getCount();
//    }
//

}

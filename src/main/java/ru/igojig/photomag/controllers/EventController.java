package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.mappers.EventMapper;
import ru.igojig.photomag.model.EventEditModel;
import ru.igojig.photomag.model.EventTableModel;
import ru.igojig.photomag.services.event.EventService;

import java.util.List;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
//@CrossOrigin(value = "http://localhost:4200")
@Slf4j
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping()
    public String eventTable(Model model) {
        List<EventTableModel> eventTableModels = eventService.findAll()
                        .stream().map(eventMapper::toTableModel).toList();
        model.addAttribute("eventTableModels", eventTableModels);
        return "/events";
    }

    @HxRequest
    @GetMapping
    public String eventTableHx(Model model) {
        List<EventTableModel> eventTableModels = eventService.findAll()
                .stream().map(eventMapper::toTableModel).toList();
        model.addAttribute("eventTableModels", eventTableModels);
        return "/fragments/events/eventTable:: eventTable";
    }

    @HxRequest
    @GetMapping("/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
//        Event event = eventService.findById(id);
       EventEditModel eventEditModel = eventMapper.toEditModel( eventService.findById(id));
        model.addAttribute("eventEditModel", eventEditModel);

        return "/fragments/events/editEvent::editEvent";
    }

    @HxRequest
    @GetMapping("/roomViewByHallId")
    public String getRooms(@RequestParam("hallId") Long hallId, @RequestParam(name = "selectedRoomId", required = false) Long selectedRoomId, Model model){
//        List<Room> rooms = roomService.findAllByHallId(hallId);
//        model.addAttribute("rooms", rooms);
        model.addAttribute("selectedRoomId",selectedRoomId);
        model.addAttribute("hallId", hallId);
        return "/fragments/events/roomView:: roomView";
    }

    @HxRequest
    @GetMapping("/festivalView")
    public String getFestivals(@RequestParam(name = "selectedFestivalId", required = false) Long selectedFestivalId, Model model){
//        List<Festival> festivals = festivalService.findAll();
//        model.addAttribute("festivals", festivals);
        model.addAttribute("selectedFestivalId", selectedFestivalId);

        return "/fragments/events/festivalView::festivalView";
    }

    @HxRequest
    @GetMapping("/hallView")
    public String getHalls(@RequestParam(name = "selectedHallId", required = false) Long selectedHallId, Model model){
//        List<Hall> halls = hallService.findAll();
//        model.addAttribute("halls", halls);
        model.addAttribute("selectedHallId", selectedHallId);

        return "/fragments/events/hallView::hallView";
    }

    @HxRequest
    @PutMapping
    public HtmxResponse updateEvent(@ModelAttribute EventEditModel eventEditModel){

        Event event=eventMapper.toEvent(eventEditModel);
        eventService.update(event);

        return HtmxResponse.builder().trigger("update").build();

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

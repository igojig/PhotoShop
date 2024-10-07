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

import java.time.LocalDate;
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
    @GetMapping("/add")
    public String addForm(Model model) {
        EventEditModel eventEditModel = EventEditModel.builder().startDate(LocalDate.now()).build();
        model.addAttribute("eventEditModel", eventEditModel);
        return "/fragments/events/editEvent::editEvent";
    }

    @HxRequest
    @GetMapping("/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        EventEditModel eventEditModel = eventMapper.toEditModel(eventService.findById(id));
        model.addAttribute("eventEditModel", eventEditModel);

        return "/fragments/events/editEvent::editEvent";
    }

    @HxRequest
    @GetMapping("/roomViewByHallId")
    public String getRooms(@RequestParam(name = "hallId", required = false) Long hallId, @RequestParam(name = "selectedRoomId", required = false) Long selectedRoomId, Model model) {
        model.addAttribute("selectedRoomId", selectedRoomId);
        model.addAttribute("hallId", hallId);
        return "/fragments/events/roomView:: roomView";
    }

    @HxRequest
    @GetMapping("/festivalView")
    public String getFestivals(@RequestParam(name = "selectedFestivalId", required = false) Long selectedFestivalId, Model model) {
        model.addAttribute("selectedFestivalId", selectedFestivalId);

        return "/fragments/events/festivalView::festivalView";
    }

    @HxRequest
    @GetMapping("/hallView")
    public String getHalls(@RequestParam(name = "selectedHallId", required = false) Long selectedHallId, Model model) {
        model.addAttribute("selectedHallId", selectedHallId);

        return "/fragments/events/hallView::hallView";
    }

    @HxRequest
    @PutMapping
    public HtmxResponse updateEvent(@ModelAttribute EventEditModel eventEditModel) {

        Event event = eventMapper.toEvent(eventEditModel);
        if (event.getId() == null) {
            eventService.create(event);
        } else {
            eventService.update(event);
        }

        return HtmxResponse.builder().trigger("update").build();

    }

    @HxRequest
    @DeleteMapping("/{id}")
    public HtmxResponse delete(@PathVariable("id") Long id){
        eventService.deleteById(id);
        return HtmxResponse.builder().trigger("update").build();
    }

}

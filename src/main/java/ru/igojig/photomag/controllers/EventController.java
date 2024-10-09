package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxReswap;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.entities.Event;
import ru.igojig.photomag.mappers.EventMapper;
import ru.igojig.photomag.mappers.FestivalMapper;
import ru.igojig.photomag.mappers.HallMapper;
import ru.igojig.photomag.mappers.RoomMapper;
import ru.igojig.photomag.model.*;
import ru.igojig.photomag.services.Hall.HallService;
import ru.igojig.photomag.services.Room.RoomService;
import ru.igojig.photomag.services.event.EventService;
import ru.igojig.photomag.services.festival.FestivalService;

import java.util.List;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
//@CrossOrigin(value = "http://localhost:4200")
@Slf4j
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final FestivalService festivalService;
    private final FestivalMapper festivalMapper;
    private final HallService hallService;
    private final HallMapper hallMapper;
    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @GetMapping()
    public String eventTable() {
        log.info("controller");
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
        EventEditModel eventEditModel = EventEditModel.builder().build();
        model.addAttribute("eventEditModel", eventEditModel);
        populateModel(model);
        return "/fragments/events/editEvent::editEvent";
    }

    @HxRequest
    @GetMapping("/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        EventEditModel eventEditModel = eventMapper.toEditModel(eventService.findById(id));
        model.addAttribute("eventEditModel", eventEditModel);
        populateModel(model, eventEditModel.getHallId());
        return "/fragments/events/editEvent::editEvent";
    }

    @HxRequest
    @GetMapping("/roomViewByHallId")
    public String getRooms(@RequestParam(name = "hallId") Long hallId, Model model) {
        populateModel(model, hallId);
        return "/fragments/rooms/roomSelectView::roomSelectView";
    }

    @HxRequest
    @PutMapping
    public HtmxResponse updateEvent(@Valid @ModelAttribute("eventEditModel") EventEditModel eventEditModel,
                                    BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            populateModel(model, eventEditModel.getHallId());
            return HtmxResponse.builder()
                    .reswap(HtmxReswap.outerHtml())
                    .retarget("#editEvent")
                    .view("/fragments/events/editEvent::editEvent")
                    .build();
        }

        Event event = eventMapper.toEntity(eventEditModel);
        if (event.getId() == null) {
            eventService.create(event);
        } else {
            eventService.update(event);
        }

        return HtmxResponse.builder().trigger("update").build();
    }

    @HxRequest
    @DeleteMapping("/{id}")
    public HtmxResponse delete(@PathVariable("id") Long id) {
        eventService.deleteById(id);
        return HtmxResponse.builder().trigger("update").build();
    }


    private void populateModel(Model model){
        List<FestivalModel> festivalModels = festivalService.findAll().stream().map(festivalMapper::toModel).toList();
        List<HallModel> hallModels = hallService.findAll().stream().map(hallMapper::toModel).toList();
        model.addAttribute("festivalModels", festivalModels);
        model.addAttribute("hallModels", hallModels);
    }

    private void populateModel(Model model, Long hallId) {
        populateModel(model);
        List<RoomModel> roomModels = roomService.findAllByHallId(hallId).stream().map(roomMapper::toModel).toList();
        model.addAttribute("roomModels", roomModels);
    }

}

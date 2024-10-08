package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.entities.Room;
import ru.igojig.photomag.services.Room.RoomService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
//@CrossOrigin(value = "http://localhost:4200")
public class RoomController {
    private final RoomService roomService;
//    private final HallService hallService;

    @GetMapping
    public String getView(Model model) {
//       List<Hall> halls=hallService.findAll();
//       model.addAttribute("halls", halls);

        return "/rooms";
    }

    @HxRequest
    @GetMapping
    public String getViewHx() {
        return "/fragments/rooms/roomView::roomView";
    }

    @HxRequest
    @GetMapping("/roomTableView")
    public String tableView(@RequestParam(name = "hallId", required = false) Long hallId, Model model) {
        List<Room> rooms = hallId == null ? new ArrayList<>() : roomService.findAllByHallId(hallId);
        model.addAttribute("rooms", rooms);
        model.addAttribute("hallId", hallId);
        return "/fragments/rooms/roomTableView::roomTableView";
    }

//    @HxRequest
//    @GetMapping()
//    public String getRoomsTable(@RequestParam("selectedHall") Long hallId, Model model){
//        log.info("hallId: {}", hallId);
//        List<Room> rooms = roomService.findAllByHallId(hallId);
//        model.addAttribute("rooms", rooms);
//        model.addAttribute("hallId", hallId);
//        return "/fragments/rooms/roomTable:: roomTable";
//    }

    @HxRequest
    @GetMapping("/addForm")
    public String getAddRoomForm(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        return "/fragments/rooms/editRoomForm::editRoomForm";
    }

    @HxRequest
    @GetMapping("/editForm/{id}")
    public String getEditForm(@PathVariable("id") Long id, Model model) {
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        return "/fragments/rooms/editRoomForm::editRoomForm";
    }

//    @HxRequest
//    @PostMapping
//    public HtmxResponse create(@ModelAttribute("room") Room room,
//                               @RequestParam("hallId") Long hallId) {
//        log.info("room: {}", room);
//        log.info("hallId: {}", hallId);
//
//        roomService.create(hallId, room);
//        return HtmxResponse.builder().trigger("updateRooms").build();
//    }

    @HxRequest
    @PutMapping
    public HtmxResponse update(@ModelAttribute("room") Room room, @RequestParam(name = "hallId", required = false) Long hallId) {
        log.info("room: {}", room);
        if(room.getId()==null){
            roomService.create(hallId, room);
        } else {
            roomService.update(room);
        }

        return HtmxResponse.builder().trigger("updateRooms").build();
    }

    @HxRequest
    @DeleteMapping("/{id}")
    public HtmxResponse delete(@PathVariable("id") Long id) {
        roomService.deleteById(id);
        return HtmxResponse.builder().trigger("updateRooms").build();
    }

    @HxRequest
    @GetMapping("/selectViewByHallId")
    public String selectViewByHallId(@RequestParam(name = "selectedRoomId", required = false) Long id,
                                     @RequestParam(name = "hallId", required = false) Long hallId, Model model) {

        List<Room> rooms = hallId == null ? new ArrayList<>() : roomService.findAllByHallId(hallId);
        model.addAttribute("rooms", rooms);
        model.addAttribute("selectedRoomId", id);
        return "/fragments/rooms/roomSelectView::roomSelectView";
    }


}

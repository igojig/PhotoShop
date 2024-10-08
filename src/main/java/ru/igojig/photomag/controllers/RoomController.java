package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.entities.Room;
import ru.igojig.photomag.mappers.RoomMapper;
import ru.igojig.photomag.model.RoomModel;
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
    private final RoomMapper roomMapper;

    @GetMapping
    public String getView(Model model) {
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
        List<RoomModel> roomModels=rooms.stream().map(roomMapper::toModel).toList();
        model.addAttribute("roomModels", roomModels);
        model.addAttribute("hallId", hallId);
        return "/fragments/rooms/roomTableView::roomTableView";
    }

    @HxRequest
    @GetMapping("/addForm")
    public String getAddRoomForm(Model model) {
        RoomModel roomModel=RoomModel.builder().build();
        model.addAttribute("roomModel", roomModel);
        return "/fragments/rooms/editRoomForm::editRoomForm";
    }

    @HxRequest
    @GetMapping("/editForm/{id}")
    public String getEditForm(@PathVariable("id") Long id, Model model) {
        RoomModel roomModel = roomMapper.toModel(roomService.findById(id));
        model.addAttribute("roomModel", roomModel);
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
    public HtmxResponse update(@ModelAttribute RoomModel roomModel, @RequestParam(name = "hallId", required = false) Long hallId) {
        log.info("room: {}", roomModel);
        Room room=roomMapper.toRoom(roomModel);
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
        List<RoomModel> roomModels=rooms.stream().map(roomMapper::toModel).toList();
        model.addAttribute("roomModels", roomModels);
        model.addAttribute("selectedRoomId", id);
        return "/fragments/rooms/roomSelectView::roomSelectView";
    }


}

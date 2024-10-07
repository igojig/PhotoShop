package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.converters.room.RoomConverter;
import ru.igojig.photomag.entities.Hall;
import ru.igojig.photomag.entities.Room;
import ru.igojig.photomag.services.Hall.HallService;
import ru.igojig.photomag.services.Room.RoomService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
//@CrossOrigin(value = "http://localhost:4200")
public class RoomController {
    private final RoomService roomService;
    private final RoomConverter roomConverter;
    private final HallService hallService;

    @GetMapping
    public String getView(Model model){
       List<Hall> halls=hallService.findAll();
       model.addAttribute("halls", halls);

       return "/rooms";
    }

    @HxRequest
    @GetMapping()
    public String getRoomsTable(@RequestParam("selectedHall") Long hallId, Model model){
        log.info("hallId: {}", hallId);
        List<Room> rooms = roomService.findAllByHallId(hallId);
        model.addAttribute("rooms", rooms);
        model.addAttribute("hallId", hallId);
        return "/fragments/rooms/roomTable:: roomTable";
    }

    @HxRequest
    @GetMapping("/addForm")
    public String getAddRoomForm(Model model){
        Room room=new Room();
        model.addAttribute("room", room);
        return "/fragments/rooms/addRoomForm::addRoomForm";
    }

    @HxRequest
    @GetMapping("/editForm/{id}")
    public String getEditForm(@PathVariable("id") Long id, Model model){
        Room room=roomService.findById(id);
        model.addAttribute("room", room);
        return "/fragments/rooms/editRoomForm::editRoomForm";
    }

    @HxRequest
    @PostMapping
    public HtmxResponse create(@ModelAttribute("room") Room room,
                               @RequestParam("hallId") Long hallId){
        log.info("room: {}", room);
        log.info("hallId: {}", hallId);

        roomService.create(hallId, room);
        return HtmxResponse.builder().trigger("updateRooms").build();
    }

    @HxRequest
    @PutMapping
    public HtmxResponse update(@ModelAttribute("room") Room room){
        log.info("room: {}", room);

        roomService.update(room);

        return HtmxResponse.builder().trigger("updateRooms").build();
    }

    @HxRequest
    @GetMapping("/cancel")
    public HtmxResponse cancel(){
        return HtmxResponse.builder().trigger("updateRooms").build();
    }

    @HxRequest
    @DeleteMapping("/{id}")
    public HtmxResponse delete(@PathVariable("id") Long id){
        roomService.deleteById(id);
        return HtmxResponse.builder().trigger("updateRooms").build();
    }

    @HxRequest
    @GetMapping("/selectViewByHallId")
    public String selectViewByHallId(@RequestParam(name = "selectedRoomId", required = false) Long id,
                                     @RequestParam("hallId") Long hallId, Model model){
        List<Room> rooms = roomService.findAllByHallId(hallId);
        model.addAttribute("rooms", rooms);
        model.addAttribute("selectedRoomId", id);
        return "/fragments/rooms/roomSelectView::roomSelectView";
    }

//    @GetMapping("/halls")
//    public String getHallData(Model model) {
//
//        List<Hall> halls = hallService.findAll();
//
//        model.addAttribute("halls", halls);
//
//       return "/fragments/rooms/hallDataView:: hallDataView";
//
//    }
//
//    @GetMapping("/rooms/{id}")
//    public RoomDto findById(@PathVariable("id") Long id) {
//        Room r = roomService.findById(id);
//        RoomDto roomDto = roomConverter.toDto(r);
//
//        return roomDto;
////        return roomConverter.entityToDto(roomService.findById(id));
//    }
//
//    @PostMapping("/halls/{id}/rooms")
//    public RoomDto create(@PathVariable("id") Long id, @RequestBody RoomDto roomDto) {
//        return roomConverter.toDto(roomService.create(id, roomConverter.toEntity(roomDto)));
//    }
//
//    @PutMapping("/rooms/{id}")
//    public RoomDto update(@PathVariable("id") Long id, @RequestBody RoomDto roomDto) {
//        return roomConverter.toDto(roomService.update(id, roomConverter.toEntity(roomDto)));
//    }
//
//    @DeleteMapping("/halls/{id}/rooms")
//    public void deleteAllByHallId(@PathVariable("id") Long id) {
//        roomService.deleteAllByHallId(id);
//    }
//
//    @DeleteMapping("/rooms/{id}")
//    public void deleteById(@PathVariable("id") Long id) {
//        roomService.deleteById(id);
//    }

}

package ru.igojig.photomag.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.entities.Room;
import ru.igojig.photomag.services.Room.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/halls/{id}/rooms")
    public List<Room> findAllByHallId(@PathVariable("id") Long id){
        return roomService.findAllByHallId(id);
    }

    @GetMapping("/rooms/{id}")
    public Room findById(@PathVariable("id") Long id){
        return roomService.findById(id);
    }

    @PostMapping("/halls/{id}/rooms")
    public Room create(@PathVariable("id") Long id, @RequestBody Room room){
        return roomService.create(id, room);
    }

    @PutMapping("/rooms/{id}")
    public Room update(@PathVariable("id") Long id, @RequestBody Room room){
        return roomService.update(id, room);
    }

    @DeleteMapping("/halls/{id}/rooms")
    public void deleteAllByHallId(@PathVariable("id") Long id){
        roomService.deleteAllByHallId(id);
    }

    @DeleteMapping("/rooms/{id}")
    public void deleteById(@PathVariable("id") Long id){
        roomService.deleteById(id);
    }

}

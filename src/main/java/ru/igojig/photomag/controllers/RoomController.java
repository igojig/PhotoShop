package ru.igojig.photomag.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.converters.room.RoomConverter;
import ru.igojig.photomag.dtos.rooms.RoomDto;
import ru.igojig.photomag.entities.Room;
import ru.igojig.photomag.services.Room.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class RoomController {
    private final RoomService roomService;
    private final RoomConverter roomConverter;

    @GetMapping("/halls/{id}/rooms")
    public List<RoomDto> findAllLightByHallId(@PathVariable("id") Long id){
        return roomService.findAllLightByHallId(id).stream()
                .map(roomConverter::toDto)
                .toList();
    }

    @GetMapping("/rooms")
    public List<RoomDto> findAllDetails(){
        return roomService.findAll().stream()
                .map(roomConverter::toDto)
                .toList();
    }

    @GetMapping("/rooms/{id}")
    public RoomDto findById(@PathVariable("id") Long id){
        Room r=roomService.findById(id);
        RoomDto roomDto =roomConverter.toDto(r);

        return roomDto;
//        return roomConverter.entityToDto(roomService.findById(id));
    }

    @PostMapping("/halls/{id}/rooms")
    public RoomDto create(@PathVariable("id") Long id, @RequestBody RoomDto roomDto){
        return roomConverter.toDto(roomService.create(id, roomConverter.toEntity(roomDto)));
    }

    @PutMapping("/rooms/{id}")
    public RoomDto update(@PathVariable("id") Long id, @RequestBody RoomDto roomDto){
        return roomConverter.toDto(roomService.update(id, roomConverter.toEntity(roomDto)));
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

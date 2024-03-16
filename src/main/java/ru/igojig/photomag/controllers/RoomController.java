package ru.igojig.photomag.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.converters.RoomConverter;
import ru.igojig.photomag.dtos.RoomDto;
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
    public List<RoomDto> findAllByHallId(@PathVariable("id") Long id){
        return roomService.findAllByHallId(id).stream()
                .map(roomConverter::entityToDto)
                .toList();
    }

    @GetMapping("/rooms")
    public List<RoomDto> findAl(){
        return roomService.findAll().stream()
                .map(roomConverter::entityToDto)
                .toList();
    }

    @GetMapping("/rooms/{id}")
    public RoomDto findById(@PathVariable("id") Long id){
        return roomConverter.entityToDto(roomService.findById(id));
    }

    @PostMapping("/halls/{id}/rooms")
    public RoomDto create(@PathVariable("id") Long id, @RequestBody RoomDto roomDto){
        return roomConverter.entityToDto(roomService.create(id, roomConverter.dtoToEntity(roomDto)));
    }

    @PutMapping("/rooms/{id}")
    public RoomDto update(@PathVariable("id") Long id, @RequestBody RoomDto roomDto){
        return roomConverter.entityToDto(roomService.update(id, roomConverter.dtoToEntity(roomDto)));
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

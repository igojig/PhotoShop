package ru.igojig.photomag.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.converters.HallConverter;
import ru.igojig.photomag.dtos.HallDto;
import ru.igojig.photomag.entities.Hall;
import ru.igojig.photomag.services.Hall.HallService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class HallController {
    private final HallService hallService;
    private final HallConverter hallConverter;

    @GetMapping("/halls")
    public List<HallDto> findAll(){
        return hallService.findAll().stream()
                .map(hallConverter::entityToDto)
                .toList();
    }

    @GetMapping("/halls/{id}")
    public HallDto findById(@PathVariable("id") Long id){
        return hallConverter.entityToDto(hallService.findById(id));
    }

    @PostMapping("/halls")
    public HallDto create(@RequestBody HallDto hallDto){
        return hallConverter.entityToDto(hallService.create(hallConverter.dtoToEntity(hallDto)));
    }

    @PutMapping("/halls/{id}")
    public HallDto update(@PathVariable("id") Long id,  @RequestBody HallDto hallDto){
        return hallConverter.entityToDto(hallService.update(id, hallConverter.dtoToEntity(hallDto)));
    }

    @DeleteMapping("/halls/{id}")
    public void deleteById(@PathVariable("id") Long id){
        hallService.deleteById(id);
    }

}

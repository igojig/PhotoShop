package ru.igojig.photomag.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import ru.igojig.photomag.converters.FestivalConverter;
import ru.igojig.photomag.dtos.FestivalDto;
import ru.igojig.photomag.entities.Festival;
import ru.igojig.photomag.services.festival.FestivalService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/festivals")
@CrossOrigin(value = "http://localhost:4200")
public class FestivalController {
    private final FestivalService festivalService;
    private final FestivalConverter festivalConverter;

    @GetMapping
    public List<FestivalDto> findAll() {
        return festivalService.findAll().stream()
                .map(festivalConverter::entityToDto)
                .toList();

    }

    @GetMapping("/{id}")
    public FestivalDto findById(@PathVariable("id") Long id) {
        return festivalConverter.entityToDto(festivalService.findById(id));
    }

    @PostMapping
    public FestivalDto create(@RequestBody FestivalDto festivalDto) {
        Festival festival = festivalConverter.dtoToEntity(festivalDto);
        festival = festivalService.create(festival);
        return festivalConverter.entityToDto(festival);
    }

    @PutMapping("/{id}")
    public FestivalDto update(@PathVariable("id") Long id, @RequestBody FestivalDto festivalDto) {
        Festival festival = festivalConverter.dtoToEntity(festivalDto);
        festival = festivalService.update(id, festival);
        return festivalConverter.entityToDto(festival);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        festivalService.deleteById(id);
    }


}

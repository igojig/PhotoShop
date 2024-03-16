package ru.igojig.photomag.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.entities.Hall;
import ru.igojig.photomag.services.Hall.HallService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class HallController {
    private final HallService hallService;

    @GetMapping("/halls")
    public List<Hall> findAll(){
        return hallService.findAll();
    }

    @GetMapping("/halls/{id}")
    public Hall findById(@PathVariable("id") Long id){
        return hallService.findById(id);
    }

    @PostMapping("/halls")
    public Hall create(@RequestBody Hall hall){
        return hallService.create(hall);
    }

    @PutMapping("/halls/{id}")
    public Hall update(@PathVariable("id") Long id,  @RequestBody Hall hall){
        return hallService.update(id, hall);
    }

    @DeleteMapping("/halls/{id}")
    public void deleteById(@PathVariable("id") Long id){
        hallService.deleteById(id);
    }

}

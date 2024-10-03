package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.converters.HallConverter;
import ru.igojig.photomag.dtos.HallDto;
import ru.igojig.photomag.entities.Hall;
import ru.igojig.photomag.services.Hall.HallService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/halls")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class HallController {
    private final HallService hallService;
    private final HallConverter hallConverter;

    @GetMapping()
    public String hallView(Model model) {
        List<HallDto> list = hallService.findAll().stream()
                .map(hallConverter::entityToDto)
                .toList();
        model.addAttribute("halls", list);
        return "/halls";
    }

    @HxRequest
    @GetMapping
    public String hallTable(Model model) {
        List<HallDto> list = hallService.findAll().stream()
                .map(hallConverter::entityToDto)
                .toList();
        model.addAttribute("halls", list);
        return "/fragments/halls/hallTable :: hallTable";
    }

    @HxRequest
    @GetMapping("/addForm")
    public String getAddForm() {
        return "/fragments/halls/addHallForm::addHall";
    }

    @HxRequest
    @PostMapping
    public HtmxResponse addHall(@RequestParam("hallName") String hallName,
                                @RequestParam("hallAddress") String hallAddress) {

        log.info("Hall name: {}", hallName);
        log.info("Hall address: {}", hallAddress);

        Hall hall = new Hall();
        hall.setAddress(hallAddress);
        hall.setName(hallName);

        hallService.create(hall);

        return HtmxResponse.builder().trigger("update").build();
    }

    @HxRequest
    @GetMapping("/editForm/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Hall hall = hallService.findById(id);
        model.addAttribute("hall", hall);
        return "/fragments/halls/editHallForm::editHall";
    }

    @HxRequest
    @GetMapping("/cancel")
    public HtmxResponse onCancel() {
        return HtmxResponse.builder().trigger("update").build();
    }

    @HxRequest
    @DeleteMapping("/{id}")
    public HtmxResponse delete(@PathVariable("id") Long id) {
        hallService.deleteById(id);
        return HtmxResponse.builder().trigger("update").build();
    }

    @HxRequest
    @PutMapping
    public HtmxResponse update(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("address") String address) {

        log.info("Hall id: {}", id);
        log.info("Hall name: {}", name);
        log.info("Hall address: {}", address);

        Hall hall = new Hall();
        hall.setName(name);
        hall.setAddress(address);
        hall.setId(id);
        hallService.update(id, hall);

        return HtmxResponse.builder().trigger("update").build();
    }

//    @GetMapping("/halls/{id}")
//    public HallDto findById(@PathVariable("id") Long id){
//        return hallConverter.entityToDto(hallService.findById(id));
//    }
//
//    @PostMapping("/halls")
//    public HallDto create(@RequestBody HallDto hallDto){
//        return hallConverter.entityToDto(hallService.create(hallConverter.dtoToEntity(hallDto)));
//    }
//
//    @PutMapping("/halls/{id}")
//    public HallDto update(@PathVariable("id") Long id,  @RequestBody HallDto hallDto){
//        return hallConverter.entityToDto(hallService.update(id, hallConverter.dtoToEntity(hallDto)));
//    }
//
//    @DeleteMapping("/halls/{id}")
//    public void deleteById(@PathVariable("id") Long id){
//        hallService.deleteById(id);
//    }

}

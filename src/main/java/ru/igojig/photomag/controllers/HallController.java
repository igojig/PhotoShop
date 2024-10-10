package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.entities.Hall;
import ru.igojig.photomag.mappers.HallMapper;
import ru.igojig.photomag.model.HallModel;
import ru.igojig.photomag.services.Hall.HallService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/halls")
@RequiredArgsConstructor
//@CrossOrigin(value = "http://localhost:4200")
public class HallController {
    private final HallService hallService;
    private final HallMapper hallMapper;

//    @GetMapping()
//    public String hallView(Model model) {
//        return "/halls";
//    }

    @HxRequest
    @GetMapping
    public String hallTable(Model model) {

        List<HallModel> hallModels=hallService.findAll().stream().map(hallMapper::toModel).toList();
        model.addAttribute("hallModels", hallModels);
        return "/fragments/halls/hallTable :: hallTable";
    }

    @HxRequest
    @GetMapping("/addForm")
    public String getAddForm(Model model) {
        HallModel hallModel= HallModel.builder().build();
        model.addAttribute("hallModel", hallModel);
        return "/fragments/halls/addHallForm::addHall";
    }

    @HxRequest
    @PostMapping
    public HtmxResponse addHall(@ModelAttribute HallModel hallModel) {
        Hall hall=hallMapper.toEntity(hallModel);
        hallService.create(hall);
        return HtmxResponse.builder().trigger("update").build();
    }

    @HxRequest
    @GetMapping("/editForm/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        HallModel hallModel = hallMapper.toModel(hallService.findById(id));
        model.addAttribute("hallModel", hallModel);
        return "/fragments/halls/editHallForm::editHall";
    }

    @HxRequest
    @DeleteMapping("/{id}")
    public HtmxResponse delete(@PathVariable("id") Long id) {
        hallService.deleteById(id);
        return HtmxResponse.builder().trigger("update").build();
    }

    @HxRequest
    @PutMapping
    public HtmxResponse update(@ModelAttribute HallModel hallModel) {
        Hall hall=hallMapper.toEntity(hallModel);
        hallService.update(hall);
        return HtmxResponse.builder().trigger("update").build();
    }
}

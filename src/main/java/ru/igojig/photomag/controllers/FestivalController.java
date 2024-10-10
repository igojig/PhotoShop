package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.entities.Festival;
import ru.igojig.photomag.mappers.FestivalMapper;
import ru.igojig.photomag.model.FestivalModel;
import ru.igojig.photomag.services.festival.FestivalService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/festivals")
public class FestivalController {

    private final FestivalService festivalService;
    private final FestivalMapper festivalMapper;

//    @GetMapping
//    public String festivalView() {
//        return "/festivals";
//    }

    @HxRequest
    @GetMapping
    public String festivalTable(Model model){

        List<FestivalModel> festivalModels = festivalService.findAll().stream().map(festivalMapper::toModel).toList();
        model.addAttribute("festivalModels", festivalModels);

        return "fragments/festivals/festivalTable :: festivalTable";
    }

    @HxRequest
    @GetMapping("/editForm/{id}")
    public String getEditForm(@PathVariable("id") Long id, Model model) {
        FestivalModel festivalModel = festivalMapper.toModel(festivalService.findById(id));
        model.addAttribute("festivalModel", festivalModel);
        return "fragments/festivals/editFestivalForm :: editFestival";
    }

    @HxRequest
    @PutMapping()
    public HtmxResponse update(@ModelAttribute FestivalModel festivalModel) {
       Festival festival=festivalMapper.toFestival(festivalModel);
        festivalService.update(festival);
        return HtmxResponse.builder().trigger("update").build();
    }

    @HxRequest
    @DeleteMapping("/{id}")
    public HtmxResponse delete(@PathVariable("id") Long id){
        festivalService.deleteById(id);
        return HtmxResponse.builder().trigger("update").build();
    }

    @HxRequest
    @GetMapping("/addForm")
    public String getAddForm(Model model){
        FestivalModel festivalModel=FestivalModel.builder().build();
        model.addAttribute("festivalModel", festivalModel);
        return "fragments/festivals/addFestivalForm :: addFestival";
    }

    @HxRequest
    @PostMapping()
    public HtmxResponse addFestival(@ModelAttribute FestivalModel festivalModel){
        Festival festival=festivalMapper.toFestival(festivalModel);
        festivalService.create(festival);
        return HtmxResponse.builder().trigger("update").build();
    }
}

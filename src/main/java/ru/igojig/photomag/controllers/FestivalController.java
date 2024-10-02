package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.converters.FestivalConverter;
import ru.igojig.photomag.dtos.FestivalDto;
import ru.igojig.photomag.entities.Festival;
import ru.igojig.photomag.services.festival.FestivalService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/festivals")
public class FestivalController {

    private final FestivalService festivalService;
    private final FestivalConverter festivalConverter;


    @GetMapping
    public String festivalView(Model model) {

        List<FestivalDto> list = festivalService.findAll().stream()
                .map(festivalConverter::entityToDto)
                .toList();

        model.addAttribute("festivals", list);

        return "festivals";
    }

    @HxRequest
    @GetMapping
    public String festivalTable(Model model){
        List<FestivalDto> list = festivalService.findAll().stream()
                .map(festivalConverter::entityToDto)
                .toList();

        model.addAttribute("festivals", list);

        return "fragments/festivalTable :: festivalTable";
    }

    @HxRequest
    @GetMapping("/updateForm/{id}")
    public String getEditForm(@PathVariable("id") Long id, Model model) {
        Festival festival = festivalService.findById(id);
        model.addAttribute("festival", festival);
        return "fragments/editFestivalForm :: editFestival";
    }

    @HxRequest
    @PutMapping("/{id}")
    public HtmxResponse update(@PathVariable("id") Long id, @RequestParam("festivalName") String name) {
        log.info("festival id {}", id);
        log.info("festival name {}", name);
        festivalService.update(id, name);
        return HtmxResponse.builder().trigger(HtmxFestivalEvents.ON_UPDATE.name()).build();
    }

    @HxRequest
    @DeleteMapping("/{id}")
    public HtmxResponse delete(@PathVariable("id") Long id){
        festivalService.deleteById(id);
        return HtmxResponse.builder().trigger(HtmxFestivalEvents.ON_DELETE.name()).build();
    }

    @HxRequest
    @GetMapping("/addForm")
    public String getAddForm(Model model){
        model.addAttribute("festivalDto", new FestivalDto());
        return "fragments/addFestivalForm :: addFestival";
    }


    @HxRequest
    @PostMapping()
    public HtmxResponse addFestival(@RequestParam("festivalName") String festivalName){
        log.info("Festival name: {}", festivalName);

        Festival festival=new Festival();
        festival.setName(festivalName);

        festivalService.create(festival);

        return HtmxResponse.builder().trigger(HtmxFestivalEvents.ON_ADD.name()).build();

    }

    @GetMapping("/cancel")
    public HtmxResponse onCancel(){
        return HtmxResponse.builder().trigger(HtmxFestivalEvents.ON_CANCEL.name()).build();
    }
}

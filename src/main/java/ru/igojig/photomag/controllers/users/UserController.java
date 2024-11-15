package ru.igojig.photomag.controllers.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.igojig.photomag.mappers.EventMapper;
import ru.igojig.photomag.model.EventFormModel;
import ru.igojig.photomag.services.event.EventService;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public String tt9(){
        return "index2";
    }

    @ResponseBody
    @GetMapping("/selectEvents")
    public List<EventFormModel> tt(){
        List<EventFormModel> models = eventService.findAll().stream().map(eventMapper::toFormModel).toList();
        return models;
    }
}

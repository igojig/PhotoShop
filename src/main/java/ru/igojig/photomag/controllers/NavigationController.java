package ru.igojig.photomag.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nav")
public class NavigationController {

    @GetMapping
    public String getNav(Model model){
        return "";
    }
}

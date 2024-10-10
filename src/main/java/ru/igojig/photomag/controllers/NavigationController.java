package ru.igojig.photomag.controllers;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxLocation;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
@Slf4j
@Controller
@RequestMapping("/nav")
public class NavigationController {

    @HxRequest
    @GetMapping
    public String navigation(Model model){
      log.info("nav update");
      return "";
    }

    @HxRequest
    @GetMapping("/events")
    public HtmxResponse events(){
        HtmxLocation htmxLocation=new HtmxLocation();
        htmxLocation.setPath("/events");
        htmxLocation.setSwap("innerHTML");
        htmxLocation.setTarget("#indexReplace");
        htmxLocation.setHeaders(Map.of("HX-Replace-Url", "true"));

        return HtmxResponse.builder()

//                .retarget("#indexReplace")
//                .reswap(HtmxReswap.innerHtml())
//                .location(htmxLocation)
                .location(htmxLocation)

//                .and(new HtmxResponse())
//                .trigger("navUpdate")
//                .replaceUrl("/")
//                .pushUrl("false")
                .build();
    }
}

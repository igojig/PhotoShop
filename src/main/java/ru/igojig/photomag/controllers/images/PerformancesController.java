package ru.igojig.photomag.controllers.images;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.igojig.photomag.components.PerformancesSelection;
import ru.igojig.photomag.mappers.ImageMapper;
import ru.igojig.photomag.model.ImageUpdateModel;
import ru.igojig.photomag.services.image.ImageService;
import ru.igojig.photomag.utils.ImageUtils;

import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class PerformancesController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final ImageUtils imageUtils;
    private final PerformancesSelection performancesSelection;

    @HxRequest
    @PostMapping("/selectedPerformances")
    public HtmxResponse selectedPerformances(@RequestParam("eventId") Long eventId,
                                             @RequestParam("performancesNumbers") Set<Long> perfNumbers){

        log.info("performances marker: {}",perfNumbers);

        List<ImageUpdateModel> imageUpdateModels = imageService.findAllByEventIdOrderByTimeAsc(eventId).stream()
                .map(imageMapper::toUpdateModel).toList();

        log.info("model {}", imageUpdateModels);

        imageUtils.generatePerformances(imageUpdateModels);

        log.info("model {}", imageUpdateModels);

        imageService.updateBatch(imageUpdateModels);


//        return "/fragments/images/view::view";



        return HtmxResponse.builder()
                .trigger("view_update")
                .build();
    }
}

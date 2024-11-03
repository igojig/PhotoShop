package ru.igojig.photomag.controllers.images;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.igojig.photomag.components.PerformancesSelection;
import ru.igojig.photomag.entities.ImageData;
import ru.igojig.photomag.mappers.EventMapper;
import ru.igojig.photomag.mappers.ImageMapper;
import ru.igojig.photomag.model.EventTableModel;
import ru.igojig.photomag.model.ImageShowModel;
import ru.igojig.photomag.services.ImageDataService;
import ru.igojig.photomag.services.event.EventService;
import ru.igojig.photomag.services.image.ImageService;
import ru.igojig.photomag.utils.ImageUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
//@CrossOrigin(value = "http://localhost:4200")
public class ImageController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final ImageDataService imageDataService;
    private final ImageUtils imageUtils;
    private final PerformancesSelection performancesSelection;


    @HxRequest
    @GetMapping("/{eventId}")
    public HtmxResponse photo(@PathVariable("eventId") Long eventId, Model model, HttpServletResponse response) {

        EventTableModel eventTableModel = eventMapper.toTableModel(eventService.findById(eventId));
        model.addAttribute("eventTableModel", eventTableModel);

        HtmxResponse htmxResponse2=   HtmxResponse.builder()
                .view("/fragments/images/photo:: photo")
                .view("/fragments/navigation_images:: navigation_images")
//                .retarget("#navUpdate")
//                .reswap(HtmxReswap.outerHtml())

                .build();

        response.addHeader("Cache-Control", "max-age=10, must-revalidate, no-transform");

        return htmxResponse2;
//        return "/fragments/images/photo:: photo";
    }

    @HxRequest
    @GetMapping("/view")
    public String view(@RequestParam("eventId") Long eventId, Model model){

        List<ImageShowModel> imageShowModels = imageService.findAllByEventIdOrderByTimeAsc(eventId).stream().map(imageMapper::toShowModel).toList();

        Map<Long, List<ImageShowModel>> imageModelMap = imageShowModels.stream()
                .collect(Collectors.groupingBy(ImageShowModel::getPerformanceNumber));

        log.info("images size: {}", imageShowModels.size());

        model.addAttribute("imageModelMap", imageModelMap);

        log.info("Sssion eventid: {}", performancesSelection.getEventId());
        performancesSelection.setEventId(eventId);
        performancesSelection.getImagesId().clear();

        return "/fragments/images/view::view";
    }



    @PostMapping("/check")
    @ResponseBody
    public void check(@RequestParam("imageId") Long imageId, @RequestParam("checked") boolean checked) {

        log.info("imageId={}", imageId);
        log.info("checked={}", checked);
        log.info("thread {}", Thread.currentThread().getName());

        boolean res;

        if(checked){
         res=   performancesSelection.getImagesId().add(imageId);
        } else {
          res=  performancesSelection.getImagesId().remove(imageId);
        }
        log.info("result={}", res);

    }



//    @HxRequest
//    @GetMapping("/generate")
//    @ResponseBody
//    public void generatePerformances() {
//        log.info("thread {}", Thread.currentThread().getName());
//        log.info("performances id's:{}", performances);
//
//
//        Map<Long, List<Long>> map = new HashMap<>();
//        List<Long> curPerformances = new ArrayList<>();
//        long currentPerformanceCount = 0;
//        for (ImageShowModel localImageShowModel : localImageShowModels) {
//
//            if (performances.contains(localImageShowModel.getId())) {
//                performances.remove(localImageShowModel.getId());
//                if (!curPerformances.isEmpty()) {
//                    map.put(currentPerformanceCount, new ArrayList<>(curPerformances));
//                    log.info("map {}", map);
//                }
//                currentPerformanceCount++;
//                curPerformances.clear();
//                curPerformances.add(localImageShowModel.getId());
//                continue;
//            }
//            curPerformances.add(localImageShowModel.getId());
//        }
//
//        map.put(currentPerformanceCount, curPerformances);
//        log.info("map {}", map);
//    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public Resource downloadImage(@PathVariable("id") Long imageDataId) {

        ImageData imageData = imageDataService.findById(imageDataId);

        return new ByteArrayResource(imageData.getBase64Content());
    }

//    @GetMapping("/{id}")
//    public void showProductImage(@PathVariable Long id,
//                                 HttpServletResponse response) throws IOException {
//        response.setContentType("image/jpeg"); // Or whatever format you wanna use
//
//        ImageData imageData=  imageDataService.findById(id);
//
//        InputStream is = new ByteArrayInputStream(imageData.getBase64Content());
//        IOUtils.copy(is, response.getOutputStream());
//    }




    @HxRequest
    @GetMapping("/main")
    public HtmxResponse toMainMenu(){
      return   HtmxResponse.builder()
                .view("/fragments/navigation::navigation")
                .build();
    }

//    @PostConstruct
//    private void init() {
//        localImageShowModels = new ArrayList<>();
//        performances = new HashSet<>();
//        log.info("image controller post construct");
//    }

}

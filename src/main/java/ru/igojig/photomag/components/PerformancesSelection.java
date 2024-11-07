package ru.igojig.photomag.components;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@Component
@SessionScope
@Getter
@Setter
@Slf4j
public class PerformancesSelection {
    private Long eventId;
    private Set<Long> imagesId;

    @PostConstruct
    public void init(){
      log.info("PerformancesSelection post construct");
      imagesId=new HashSet<>();
    }
}

package ru.igojig.photomag.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EventEditModel {
    private Long id;
    private String name;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;
    private Long festivalId;
    private Long hallId;
    private Long roomId;
}

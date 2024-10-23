package ru.igojig.photomag.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class ImageUpdateModel {
    private Long id;
    private String fileName;
    private String filePath;
    private Long performanceNumber;
    private Long eventId;
    private LocalDateTime dateTime;
}
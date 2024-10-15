package ru.igojig.photomag.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ImageShowModel {
    private Long id;
    private String fileName;
    private String filePath;

    private LocalDateTime dateTime;
}

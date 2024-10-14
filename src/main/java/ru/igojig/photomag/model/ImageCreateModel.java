package ru.igojig.photomag.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageCreateModel {
    private Long id;
    private String fileName;
    private String filePath;
    private Long eventId;
}

package ru.igojig.photomag.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HallModel {
    private Long id;
    private String name;
    private String address;
}

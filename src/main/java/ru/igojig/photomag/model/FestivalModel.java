package ru.igojig.photomag.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FestivalModel {
    private Long id;
    private String name;
}

package ru.igojig.photomag.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PagebleEventDto {
    private List<EventDto> eventDtoList;
    private boolean last;
    private boolean first;
}

package ru.igojig.photomag.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private FestivalDto festivalDto;
    private RoomDto roomDto;
}

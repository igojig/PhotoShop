package ru.igojig.photomag.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.igojig.photomag.dtos.events.EventDto;

@Data
@NoArgsConstructor
public class ImageDto {
    private Long id;
    private String fileName;
    private String filePath;
    private EventDto eventDto;

}

package ru.igojig.photomag.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SaveToDropBoxException extends RuntimeException{
    private Long imageId;
    private String fileName;
    private String message;
}

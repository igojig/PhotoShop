package ru.igojig.photomag.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResizeException extends RuntimeException{
    private String fileName;
    private String message;

    public ResizeException(String message, String fileName){
        super(message);
        this.message=message;
        this.fileName=fileName;
    }
}

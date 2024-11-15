package ru.igojig.photomag.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResizeException extends UploadException{

    public ResizeException(String message, String fileName){
        super(message, fileName);
    }
}

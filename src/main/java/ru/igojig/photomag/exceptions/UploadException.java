package ru.igojig.photomag.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UploadException extends RuntimeException{
    private Long imageId;
    private Long eventId;
    private String fileName;

    private String message;

    public UploadException(String message, String fileName){
        super(message);
        this.message=message;
        this.fileName=fileName;
    }

    public UploadException(String message){
        super(message);
        this.message=message;
    }


}

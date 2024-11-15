package ru.igojig.photomag.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventNotFoundException extends RuntimeException {
    private String message;

    public EventNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}

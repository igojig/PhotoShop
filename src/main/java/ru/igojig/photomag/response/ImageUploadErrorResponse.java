package ru.igojig.photomag.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageUploadErrorResponse {
    private Long imageId;
    private Long eventId;
    private String fileName;
    private String message;
}

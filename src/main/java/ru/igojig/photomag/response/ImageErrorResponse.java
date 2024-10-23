package ru.igojig.photomag.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageErrorResponse {
    private Long imageId;
    private String fileName;
    private String message;
}

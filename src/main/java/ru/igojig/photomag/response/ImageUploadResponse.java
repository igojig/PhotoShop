package ru.igojig.photomag.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageUploadResponse {
    private Long imageId;
    private String fileName;

}

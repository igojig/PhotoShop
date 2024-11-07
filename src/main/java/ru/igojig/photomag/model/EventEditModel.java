package ru.igojig.photomag.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EventEditModel {
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull
    private Long hallId;

    @NotNull
    private Long roomId;
}

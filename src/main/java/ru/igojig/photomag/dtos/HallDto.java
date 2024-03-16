package ru.igojig.photomag.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallDto {
    private Long id;
    private String name;
    private String address;
}

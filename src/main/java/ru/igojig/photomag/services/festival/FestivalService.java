package ru.igojig.photomag.services.festival;

import ru.igojig.photomag.entities.Festival;

import java.util.List;


public interface FestivalService {
    List<Festival> findAll();

    Festival create(Festival festival);

    Festival update(Festival festival);

    void deleteById(Long id);

    Festival findById(Long id);
}

package ru.igojig.photomag.services.Hall;

import ru.igojig.photomag.entities.Hall;

import java.util.List;

public interface HallService {
    List<Hall> findAll();

    Hall findById(Long id);

    Hall create(Hall hall);

    Hall update(Long id, Hall hall);

    void deleteById(Long id);
}

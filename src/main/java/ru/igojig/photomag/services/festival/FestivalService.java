package ru.igojig.photomag.services.festival;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.igojig.photomag.entities.Festival;
import ru.igojig.photomag.repositories.FestivalRepository;

import java.util.List;


public interface FestivalService {
    List<Festival> findAll();

    Festival create(Festival festival);

    Festival update(Long id, String festivalName);

    void deleteById(Long id);

    Festival findById(Long id);
}

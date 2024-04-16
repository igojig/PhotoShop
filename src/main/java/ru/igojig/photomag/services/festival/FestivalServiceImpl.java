package ru.igojig.photomag.services.festival;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.igojig.photomag.entities.Festival;
import ru.igojig.photomag.repositories.FestivalRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalServiceImpl implements FestivalService {
    private final FestivalRepository festivalRepository;
    @Override
//    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<Festival> findAll() {
        return festivalRepository.findAll();
    }

    @Override
    @Transactional
    public Festival create(Festival festival) {
        Festival fest=new Festival();
//        fest.setId(festival.getId());
        fest.setName(festival.getName());
//        festival.setId(null);
        return festivalRepository.save(fest);
    }

    @Override
    @Transactional
    public Festival update(Long id, Festival festival) {
        Festival newFest = festivalRepository.findById(id).orElseThrow();
        newFest.setName(festival.getName());
        return newFest;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        festivalRepository.deleteById(id);
    }

    @Override
    public Festival findById(Long id) {
        return festivalRepository.findById(id).orElseThrow();
    }
}

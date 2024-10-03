package ru.igojig.photomag.services.Hall;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.igojig.photomag.entities.Hall;
import ru.igojig.photomag.repositories.HallRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService{
    private final HallRepository hallRepository;
    @Override
    public List<Hall> findAll() {
        return hallRepository.findAll();
    }

    @Override
    public Hall findById(Long id) {
        return hallRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Hall create(Hall hall) {
//        Hall newHall=new Hall();
//        newHall.setName(hall.getName());
//        newHall.setAddress(newHall.getAddress());
        return hallRepository.save(hall);
    }

    @Override
    @Transactional
    public Hall update(Long id, Hall hall) {
        Hall updatedHall = hallRepository.findById(id).orElseThrow();
        updatedHall.setName(hall.getName());
        updatedHall.setAddress(hall.getAddress());
        return updatedHall;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        hallRepository.deleteById(id);
    }
}

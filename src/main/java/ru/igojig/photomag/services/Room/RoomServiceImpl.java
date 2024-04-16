package ru.igojig.photomag.services.Room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.igojig.photomag.entities.Hall;
import ru.igojig.photomag.entities.Room;
import ru.igojig.photomag.repositories.RoomRepository;
import ru.igojig.photomag.services.Hall.HallService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HallService hallService;

    @Override
    public List<Room> findAllLightByHallId(Long id) {
        return roomRepository.findByHallId(id);
    }

    @Override

    public Room findById(Long id) {
        Room r=roomRepository.findById(id).orElseThrow();
        return  r;
//        return roomRepository.findByIdWithQuery(id).orElseThrow();
    }

    @Override
    public List<Room> findAll() {
//        return roomRepository.findAll();
        return roomRepository.findAllWithDetails();
    }

    @Override
    @Transactional
    public Room create(Long id, Room room) {
        Room newRoom=new Room();
        Hall hall=hallService.findById(id);
        newRoom.setName(room.getName());
        newRoom.setHall(hall);
        return roomRepository.save(newRoom);
    }

    @Override
    @Transactional
    public Room update(Long id, Room room) {
        Room updatedRoom = roomRepository.findById(id).orElseThrow();
        updatedRoom.setName(room.getName());
        return updatedRoom;
    }

    @Override
    @Transactional
    public void deleteAllByHallId(Long id) {
        roomRepository.deleteByHallId(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }
}

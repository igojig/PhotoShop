package ru.igojig.photomag.services.Room;

import ru.igojig.photomag.dtos.RoomDto;
import ru.igojig.photomag.entities.Room;

import java.util.List;

public interface RoomService {

    List<Room> findAllByHallId(Long id);

    Room findById(Long id);

    Room create(Long id, Room room);

    Room update(Long id, Room room);

    void deleteAllByHallId(Long id);

    void deleteById(Long id);

    List<Room> findAll();
}

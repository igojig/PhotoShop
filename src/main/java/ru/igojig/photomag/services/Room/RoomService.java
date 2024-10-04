package ru.igojig.photomag.services.Room;

import ru.igojig.photomag.entities.Room;

import java.util.List;

public interface RoomService {

    List<Room> findAllByHallId(Long id);

    Room findById(Long id);

    Room create(Long hallId, Room room);

    Room update(Room room);

    void deleteAllByHallId(Long id);

    void deleteById(Long id);

    List<Room> findAll();
}

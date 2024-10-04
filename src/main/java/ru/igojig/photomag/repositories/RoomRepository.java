package ru.igojig.photomag.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.igojig.photomag.entities.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r  where r.hall.id= :id")
    List<Room> findByHallId(@Param("id") Long id);

    @Query("Select r From Room r join fetch r.hall")
    List<Room> findAllWithDetails();


    @Override
    @EntityGraph(attributePaths = {"hall"})
//    @Query("Select r From Room r join fetch r.hall where r.id= :id")
//    @Query("Select r From Room r where r.id= :id")
    Optional<Room> findById(Long id);


    @Modifying
    @Query("Delete from Room r where r.hall.id= :id")
    void deleteByHallId(@Param ("id") Long id);
}

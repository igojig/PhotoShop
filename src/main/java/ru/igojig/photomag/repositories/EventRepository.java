package ru.igojig.photomag.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.igojig.photomag.entities.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @EntityGraph(attributePaths = {"room", "festival", "room.hall"})
    List<Event> findAll();

    @EntityGraph(attributePaths = {"room", "festival", "room.hall"})
    Optional<Event> findById(Long id);

}

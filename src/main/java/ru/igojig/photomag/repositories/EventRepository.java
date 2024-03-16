package ru.igojig.photomag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.igojig.photomag.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {


}

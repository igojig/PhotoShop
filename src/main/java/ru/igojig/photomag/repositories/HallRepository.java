package ru.igojig.photomag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.igojig.photomag.entities.Hall;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
}

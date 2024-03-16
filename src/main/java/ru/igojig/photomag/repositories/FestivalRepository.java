package ru.igojig.photomag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.igojig.photomag.entities.Festival;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long> {
}

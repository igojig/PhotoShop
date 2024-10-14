package ru.igojig.photomag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.igojig.photomag.entities.ImageData;

@Repository
public interface ImageDataRepository extends JpaRepository<ImageData, Long> {

//    @Transactional
//    @Query("Select idt from ImageData idt join fetch idt.image img where img.event.id = :eventId")
//    List<ImageData> findAllByEventId(@Param("eventId") Long eventId);

}

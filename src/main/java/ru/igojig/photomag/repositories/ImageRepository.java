package ru.igojig.photomag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.igojig.photomag.entities.Image;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {



//    @Query(value = "select * from images where images.event_id= :id", nativeQuery = true)
    @Query("Select i from Image i join fetch i.event e join fetch e.festival join fetch e.room r join fetch r.hall where e.id= :id")
    List<Image> findAllByEventId(@Param("id") Long id);

//    @Query("Select i from Image i where i.id= :id")
//    Optional<Image> find(@Param("id") Long id);
//    Optional<Image> findById(@ParaLong id);

    @Query("Select i from Image i join fetch i.event e join fetch e.festival join fetch e.room r join fetch r.hall where i.id= :id")
    Optional<Image> findById(Long id);
}

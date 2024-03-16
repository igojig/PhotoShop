package ru.igojig.photomag.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

// концертный зал
@Entity
@Table(name = "halls")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hall {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

//    @OneToMany(mappedBy = "concertHall", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<ConcertHallPlacements> concertHallPlacementsList;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

//    public void addConcertHallPlacements(ConcertHallPlacements concertHallPlacements){
//        this.concertHallPlacementsList.add(concertHallPlacements);
//        concertHallPlacements.setConcertHall(this);
//    }
}

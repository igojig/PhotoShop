package ru.igojig.photomag.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

// помещение в концертном зале
@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hall_id")
    public Hall hall;


//    @OneToMany(mappedBy = "placement", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<ConcertHallPlacements> concertHallPlacementsList;


//    public void addConcertHallPlacements(ConcertHallPlacements concertHallPlacements) {
//        if (concertHallPlacementsList == null) {
//            concertHallPlacementsList = new ArrayList<>();
//        }
//        this.concertHallPlacementsList.add(concertHallPlacements);
//        concertHallPlacements.setPlacement(this);
//    }

}

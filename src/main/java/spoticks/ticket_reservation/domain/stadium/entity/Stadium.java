package spoticks.ticket_reservation.domain.stadium.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Stadium {

    @Id
    @Column(name = "stadium_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String stadiumName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StadiumType stadiumType;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

}
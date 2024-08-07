package spoticks.ticket_reservation.domain.seat.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import spoticks.ticket_reservation.domain.stadium.entity.Stadium;

@Entity
@NoArgsConstructor
public class Seat {

    @Id
    @Column(name = "seat_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @Column(nullable = false)
    private int seatPrice;

    @Column(nullable = false)
    private String seatPosition;

    @Column(nullable = false)
    private String seatRow;

    @Column(nullable = false)
    private String seatNumber;
}
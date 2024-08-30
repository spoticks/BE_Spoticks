package spoticks.ticket_reservation.domain.seat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoticks.ticket_reservation.domain.game.entity.Game;

@Entity
@Getter
@NoArgsConstructor
public class Seat {

    @Id
    @Column(name = "seat_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnore
    private Game game;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Column(nullable = false)
    private int seatPrice;

    @Column(nullable = false)
    private String seatPosition;

    @Column(nullable = false)
    private String seatRow;

    @Column(nullable = false)
    private String seatNumber;

    public void preempt() {
        status = SeatStatus.PROCESSING;
    }

    public void complete() {
        status = SeatStatus.RESERVED;
    }

    public void release() {
        status = SeatStatus.AVAILABLE;
    }

    public void cancel() {
        status = SeatStatus.AVAILABLE;
    }
}
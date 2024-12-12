package spoticks.ticket_reservation.domain.seat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.reservation.entity.Reservation;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Seat {

    @Id
    @Column(name = "seat_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnore
    private Game game;

    @JsonIgnore
    @ManyToMany(mappedBy = "seats")
    private List<Reservation> reservations = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Column(nullable = false)
    private int seatPrice;

    @Column(nullable = false)
    private String seatPosition;

    @Column(nullable = false)
    private int seatRow;

    @Column(nullable = false)
    private int seatNumber;

    public void preempt() {
        status = SeatStatus.PROCESSING;
    }

    public void complete() {
        status = SeatStatus.RESERVED;
    }

    public void release() {
        status = SeatStatus.AVAILABLE;
    }

    @Builder
    public Seat(Game game, int seatPrice, String seatPosition, int seatRow, int seatNumber) {
        this.game = game;
        this.status = SeatStatus.AVAILABLE;
        this.seatPrice = seatPrice;
        this.seatPosition = seatPosition;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

}
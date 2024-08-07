package spoticks.ticket_reservation.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ReservationSeat {

    @Id
    @Column(name = "reservation_seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "seat_id")
//    private Seat seat;

//    @Builder
//    public ReservationSeat(Reservation reservation, Seat seat) {
//        this.reservation = reservation;
//        this.seat = seat;
//    }

}

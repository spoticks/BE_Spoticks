package spoticks.ticket_reservation.domain.reservation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.member.entity.Member;
import spoticks.ticket_reservation.domain.seat.entity.Seat;
import spoticks.ticket_reservation.global.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Reservation extends BaseTimeEntity {

    @Id
    @Column(name = "reservation_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false, updatable = false)
    private int totalPrice;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "reservation_seat",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats = new ArrayList<>();

    @Builder
    public Reservation(Member member, Game game, int totalPrice, List<Seat> seatList) {
        this.member = member;
        this.game = game;
        this.status = ReservationStatus.COMPLETED;
        this.totalPrice = totalPrice;
        seats.addAll(seatList);
    }

    public void cancelReservation() {
        this.status = ReservationStatus.CANCELED;
    }

}

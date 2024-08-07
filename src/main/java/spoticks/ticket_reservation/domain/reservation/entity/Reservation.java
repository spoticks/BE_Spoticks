package spoticks.ticket_reservation.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoticks.ticket_reservation.domain.member.entity.Member;
import spoticks.ticket_reservation.global.common.BaseTimeEntity;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "game_id")
//    private Game game;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false, updatable = false)
    private int totalPrice;

//    @Builder
//    public Reservation(Member member, Game game, int totalPrice) {
//        this.member = member;
//        this.game = game;
//        this.totalPrice = totalPrice;
//    }

    public void cancelReservation() {
        this.status = ReservationStatus.CANCELED;
    }

}

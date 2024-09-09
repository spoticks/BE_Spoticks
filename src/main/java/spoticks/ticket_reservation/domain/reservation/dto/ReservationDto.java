package spoticks.ticket_reservation.domain.reservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoticks.ticket_reservation.domain.game.dto.GameDto;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.member.entity.Member;
import spoticks.ticket_reservation.domain.reservation.entity.Reservation;
import spoticks.ticket_reservation.domain.reservation.entity.ReservationStatus;
import spoticks.ticket_reservation.domain.seat.entity.Seat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDto {

    @Getter
    @NoArgsConstructor
    public static class CheckSeat {

        private List<Long> seatIds;

    }

    @Getter
    @NoArgsConstructor
    public static class Req {

        private List<Long> seatIds;

        @NotNull
        private Integer totalPrice;

        @NotNull
        private Long memberId;

        public Reservation toEntity(Member member, Game game, List<Seat> seatList) {
            return Reservation.builder()
                    .member(member)
                    .game(game)
                    .totalPrice(this.totalPrice)
                    .seatList(seatList)
                    .build();
        }

    }

    @Getter
    public static class MyPageRes {

        private final long reservationId;
        private final GameDto.SimpleRes game;

        public MyPageRes(long reservationId, GameDto.SimpleRes game) {
            this.reservationId = reservationId;
            this.game = game;
        }
    }

    public static List<MyPageRes> toMyPageResList(List<Reservation> reservations) {
        List<MyPageRes> myPageResList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            myPageResList.add(new MyPageRes(reservation.getId(),
                    new GameDto.SimpleRes(reservation.getGame())));
        }
        return myPageResList;
    }

    @Getter
    public static class Res {

        private final GameDto.SimpleRes game;
        private final LocalDateTime createdAt;
        private final String memberName;
        private final ReservationStatus reservationStatus;
        private final int totalPrice;
        private final List<SeatRes> seats;

        @Builder
        public Res(Reservation reservation, Member member) {
            this.game = new GameDto.SimpleRes(reservation.getGame());
            this.createdAt = reservation.getCreatedAt();
            this.memberName = member.getMemberName();
            this.reservationStatus = reservation.getStatus();
            this.totalPrice = reservation.getTotalPrice();
            this.seats = toSeatResList(reservation.getSeats());
        }
    }

    @Getter
    public static class SeatRes {
        String seatPosition;
        int seatRow;
        int seatNum;

        public SeatRes(Seat seat) {
            this.seatPosition = seat.getSeatPosition();
            this.seatRow = seat.getSeatRow();
            this.seatNum = seat.getSeatNumber();
        }
    }

    public static List<SeatRes> toSeatResList(List<Seat> seatList) {
        List<SeatRes> seatResList = new ArrayList<>();
        for(Seat seat : seatList) {
            seatResList.add(new SeatRes(seat));
        }
        return seatResList;
    }

}

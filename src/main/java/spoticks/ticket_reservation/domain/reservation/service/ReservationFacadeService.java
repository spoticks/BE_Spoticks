package spoticks.ticket_reservation.domain.reservation.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.game.exception.GameTimeOutOfBoundException;
import spoticks.ticket_reservation.domain.game.service.GameService;
import spoticks.ticket_reservation.domain.member.entity.Member;
import spoticks.ticket_reservation.domain.member.service.MemberService;
import spoticks.ticket_reservation.domain.reservation.dto.ReservationDto;
import spoticks.ticket_reservation.domain.reservation.entity.Reservation;
import spoticks.ticket_reservation.domain.reservation.entity.ReservationStatus;
import spoticks.ticket_reservation.domain.reservation.exception.CancellationPeriodExpiredException;
import spoticks.ticket_reservation.domain.seat.entity.Seat;
import spoticks.ticket_reservation.domain.seat.exception.SeatAlreadySelectedException;
import spoticks.ticket_reservation.domain.seat.service.SeatService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationFacadeService {

    private final ReservationService reservationService;
    private final SeatService seatService;
    private final GameService gameService;
    private final MemberService memberService;

    public void preemptSeatList(long gameId, List<Long> seatIds) {
        for (long seatId : seatIds) {
            if (!seatService.isSeatAvailable(seatId)) {
                throw new SeatAlreadySelectedException();
            }
        }

        for (long seatId : seatIds) {
            seatService.preemptSeat(seatId);
        }
    }

    public void makeReservation(long gameId, ReservationDto.Req dto) {
        Game game = gameService.findById(gameId);

        if (!gameService.isGameOpen(game)) {
            throw new GameTimeOutOfBoundException();
        }

        List<Seat> seatList = new ArrayList<>();

        for (long seatId : dto.getSeatIds()) {
            Seat seat = seatService.findById(seatId);
            seatService.reserveSeat(seat);
            seatList.add(seat);
        }

        Member member = memberService.findById(dto.getMemberId());
        reservationService.saveReservation(dto.toEntity(member, game, seatList));
    }

    public ReservationDto.Res getReservation(Long reservationId, Long memberId) {
        Member member = memberService.findById(memberId);
        Reservation reservation = reservationService.findReservationById(reservationId);

        return ReservationDto.Res.builder()
                .reservation(reservation)
                .member(member)
                .build();
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationService.findReservationById(reservationId);

        if (!isCancellationAllowed(reservation)) {
            throw new CancellationPeriodExpiredException();
        }

        reservation.cancelReservation();
        reservationService.saveReservation(reservation);

        for (Seat seat : reservation.getSeats()) {
            seatService.releaseSeat(seat);
        }
    }

    public boolean isCancellationAllowed(Reservation reservation) {
        return LocalDateTime.now().isBefore(reservation.getGame().getTimeOffSale());
    }

    public Page<Reservation> getReservationsByStatus(Long memberId, String status, int page) {
        Member member = memberService.findById(memberId);
        if (status.equals("COMPLETE")) return reservationService.findReservationsByMember(page, member, ReservationStatus.COMPLETED);
        else return reservationService.findReservationsByMember(page, member, ReservationStatus.CANCELED);
    }

}

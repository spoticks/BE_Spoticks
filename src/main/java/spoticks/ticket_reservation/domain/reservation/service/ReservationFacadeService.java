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
import spoticks.ticket_reservation.domain.seat.exception.SeatPreemptException;
import spoticks.ticket_reservation.domain.seat.service.SeatPreemptionService;
import spoticks.ticket_reservation.domain.seat.service.SeatService;
import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.AccessDeniedException;
import spoticks.ticket_reservation.global.auth.service.AuthorizationUtil;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationFacadeService {

    private final ReservationService reservationService;
    private final SeatService seatService;
    private final SeatPreemptionService preemptionService;
    private final GameService gameService;
    private final MemberService memberService;

    public void preemptSeatList(long gameId, List<Long> seatIds) {
        for (long seatId : seatIds) {
            if (!seatService.isSeatAvailable(seatId)) {
                throw new SeatPreemptException(ErrorCode.SEAT_ALREADY_SELECTED);
            }
        }

        long memberId = AuthorizationUtil.getMemberId();

        for (long seatId : seatIds) {
            preemptionService.preemptSeat(String.valueOf(seatId), memberId);
        }
    }

    public void makeReservation(long gameId, ReservationDto.Req dto) {
        Game game = gameService.findById(gameId);

        if (!gameService.isGameOpen(game)) {
            throw new GameTimeOutOfBoundException();
        }

        long memberId = AuthorizationUtil.getMemberId();
        final Member member = memberService.findById(memberId);

        List<Seat> seatList = new ArrayList<>();

        for (long seatId : dto.getSeatIds()) {
            preemptionService.verifyPreemptionMember(String.valueOf(seatId), memberId);
            Seat seat = seatService.findById(seatId);
            seatService.reserveSeat(seat);
            seatList.add(seat);
        }

        reservationService.saveReservation(dto.toEntity(member, game, seatList));
    }

    public ReservationDto.Res getReservation(Long reservationId) {
        Reservation reservation = reservationService.findReservationById(reservationId);
        long memberId = AuthorizationUtil.getMemberId();

        if (!reservation.getMember().getId().equals(memberId)) {
            throw new AccessDeniedException("Cannot access this reservation");
        }

        final Member member = memberService.findById(memberId);

        return ReservationDto.Res.builder()
                .reservation(reservation)
                .member(member)
                .build();
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationService.findReservationById(reservationId);
        long memberId = AuthorizationUtil.getMemberId();

        if (!reservation.getMember().getId().equals(memberId)) {
            throw new AccessDeniedException("Cannot cancel reservation");
        }

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
        return ZonedDateTime.now().isBefore(reservation.getGame().getTimeOffSale());
    }

    public Page<Reservation> getReservationsByStatus(String status, int page) {
        long memberId = AuthorizationUtil.getMemberId();
        final Member member = memberService.findById(memberId);
        if (status.equals("COMPLETE")) return reservationService.findReservationsByMember(page, member, ReservationStatus.COMPLETED);
        else return reservationService.findReservationsByMember(page, member, ReservationStatus.CANCELED);
    }

}

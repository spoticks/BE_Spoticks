package spoticks.ticket_reservation.domain.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spoticks.ticket_reservation.domain.reservation.dto.ReservationDto;
import spoticks.ticket_reservation.domain.reservation.entity.Reservation;
import spoticks.ticket_reservation.domain.reservation.service.ReservationFacadeService;
import spoticks.ticket_reservation.global.common.MultiResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationFacadeService reservationFacadeService;

    @GetMapping("/games/{gameId}/preempt")
    public ResponseEntity preemptSeat(@PathVariable("gameId") Long gameId, @RequestBody ReservationDto.CheckSeat dto) {
        reservationFacadeService.preemptSeatList(gameId, dto.getSeatIds());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/games/{gameId}/reserve")
    public ResponseEntity createReservation(@PathVariable Long gameId, @Valid @RequestBody ReservationDto.Req dto) {
        reservationFacadeService.makeReservation(gameId, dto);
        String response = "예매가 완료되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/reservation")
    public ResponseEntity getReservations(@RequestParam String status, @RequestParam Long memberId, @RequestParam(defaultValue = "1") int page) {
        Page<Reservation> reservationPage = reservationFacadeService.getReservationsByStatus(memberId, status, page);
        List<ReservationDto.MyPageRes> reservationList = ReservationDto.toMyPageResList(reservationPage.getContent());
        return new ResponseEntity<>(new MultiResponseDto<>(reservationList, reservationPage), HttpStatus.OK);
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity getReservation(@PathVariable Long reservationId, @RequestParam Long memberId) {
        ReservationDto.Res dto = reservationFacadeService.getReservation(reservationId, memberId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity cancelReservation(@PathVariable Long reservationId) {
        reservationFacadeService.cancelReservation(reservationId);
        String response = "예매가 취소되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

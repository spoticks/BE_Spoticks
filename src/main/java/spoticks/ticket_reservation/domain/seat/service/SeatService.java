package spoticks.ticket_reservation.domain.seat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.seat.entity.Seat;
import spoticks.ticket_reservation.domain.seat.entity.SeatStatus;
import spoticks.ticket_reservation.domain.seat.exception.SeatNotFoundException;
import spoticks.ticket_reservation.domain.seat.repository.SeatRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public Seat findById(Long id) {
        final Optional<Seat> seat = seatRepository.findById(id);
        seat.orElseThrow(SeatNotFoundException::new);
        return seat.get();
    }

    @Transactional(readOnly = true)
    public List<Seat> findAllByPosition(long gameId, String seatPosition) {
        return seatRepository.findAllByGameAndSeatPosition(gameId, seatPosition);
    }

    @Transactional(readOnly = true)
    public boolean isSeatAvailable(long seatId) {
        return seatRepository.existsByIdAndStatus(seatId, SeatStatus.AVAILABLE);
    }

    public void preemptSeat(long seatId) {
        Seat seat = findById(seatId);
        seat.preempt();
        seatRepository.save(seat);
    }

    public void reserveSeat(Seat seat) {
        seat.complete();
        seatRepository.save(seat);
    }

    public void releaseSeat(Seat seat) {
        seat.release();
        seatRepository.save(seat);
    }

    public void saveSeat(Seat seat) {
        seatRepository.save(seat);
    }

}

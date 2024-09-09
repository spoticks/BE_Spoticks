package spoticks.ticket_reservation.domain.seat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.seat.entity.Seat;
import spoticks.ticket_reservation.domain.seat.entity.SeatStatus;
import spoticks.ticket_reservation.domain.seat.exception.SeatNotFoundException;
import spoticks.ticket_reservation.domain.seat.repository.SeatRepository;
import spoticks.ticket_reservation.domain.stadium.entity.StadiumType;

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

    public void registerSeat(Game game, StadiumType type) {
        int ROW = 5;
        int COL = 2;
        int num = 1;
        for (StadiumType.Section section : type.getSections()) {
            for(int r = 1; r <= ROW; r++) {
                for (int c = 1; c <= COL; c++) {
                    Seat seat = Seat.builder()
                            .game(game)
                            .seatPrice(section.getPrice())
                            .seatPosition(section.getSeatPosition())
                            .seatRow(r)
                            .seatNumber(num++)
                            .build();
                    game.registerSeat(seat);
                }
            }
        }
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

}

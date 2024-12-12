package spoticks.ticket_reservation.domain.seat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spoticks.ticket_reservation.domain.seat.entity.Seat;
import spoticks.ticket_reservation.domain.seat.entity.SeatStatus;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    boolean existsByIdAndStatus(long id, SeatStatus status);

    @Query("SELECT s from Seat s where s.game.id = :gameId and s.seatPosition = :seatPosition")
    List<Seat> findAllByGameAndSeatPosition(long gameId, String seatPosition);

}

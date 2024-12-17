package spoticks.ticket_reservation.domain.seat.repository;

import org.springframework.data.repository.CrudRepository;
import spoticks.ticket_reservation.domain.seat.entity.SeatPreemption;

public interface SeatPreemptionRepository extends CrudRepository<SeatPreemption, String> {
}

package spoticks.ticket_reservation.domain.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spoticks.ticket_reservation.domain.sport.entity.Sport;

import java.util.Optional;

public interface SportRepository extends JpaRepository<Sport, Long> {

    Optional<Sport> findBySportName(String sportName);

}

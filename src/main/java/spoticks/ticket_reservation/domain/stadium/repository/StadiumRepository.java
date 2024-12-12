package spoticks.ticket_reservation.domain.stadium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spoticks.ticket_reservation.domain.stadium.entity.Stadium;

import java.util.Optional;

public interface StadiumRepository extends JpaRepository<Stadium, Long> {

    Optional<Stadium> findByStadiumName(String stadiumName);

}

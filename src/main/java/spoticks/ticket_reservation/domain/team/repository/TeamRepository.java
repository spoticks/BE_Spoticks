package spoticks.ticket_reservation.domain.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spoticks.ticket_reservation.domain.team.entity.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByTeamName(String teamName);

}

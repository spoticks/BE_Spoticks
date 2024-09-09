package spoticks.ticket_reservation.domain.game.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.sport.entity.Sport;
import spoticks.ticket_reservation.domain.team.entity.Team;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findFirstByTimeOnSaleBeforeAndTimeOffSaleAfter(LocalDateTime timeOnSaleBefore, LocalDateTime timeOffSaleAfter);

    List<Game> findByTimeOffSaleBetween(LocalDateTime start, LocalDateTime end);

    Page<Game> findBySport(Sport sport, Pageable pageable);

    List<Game> findByHomeTeamOrAwayTeamOrderByGameStartTime(Team homeTeam, Team awayTeam);

}

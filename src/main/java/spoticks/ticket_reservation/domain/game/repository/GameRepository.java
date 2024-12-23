package spoticks.ticket_reservation.domain.game.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.sport.entity.Sport;
import spoticks.ticket_reservation.domain.team.entity.Team;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findFirstByTimeOnSaleBeforeAndTimeOffSaleAfter(ZonedDateTime timeOnSaleBefore, ZonedDateTime timeOffSaleAfter);

    List<Game> findByTimeOffSaleBetween(ZonedDateTime start, ZonedDateTime end);

    Page<Game> findBySport(Sport sport, Pageable pageable);

    Page<Game> findBySportAndTimeOffSaleAfter(Sport sport, ZonedDateTime timeOffSaleAfter, Pageable pageable);

    Page<Game> findByHomeTeamAndTimeOffSaleAfter(Team homeTeam, ZonedDateTime timeOffSaleAfter, Pageable pageable);

    @Query("SELECT g FROM Game g " +
            "WHERE g.timeOffSale > :timeOffSaleAfter " +
            "AND (g.homeTeam = :homeTeam OR g.awayTeam = :awayTeam)")
    Page<Game> findByHomeTeamOrAwayTeamAndTimeOffSaleAfter(@Param("timeOffSaleAfter") ZonedDateTime timeOffSaleAfter,
                                                           @Param("homeTeam") Team homeTeam,
                                                           @Param("awayTeam") Team awayTeam, Pageable pageable);

}

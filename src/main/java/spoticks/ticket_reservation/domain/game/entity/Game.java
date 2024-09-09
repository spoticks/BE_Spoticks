package spoticks.ticket_reservation.domain.game.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoticks.ticket_reservation.domain.game.dto.GameDto;
import spoticks.ticket_reservation.domain.seat.entity.Seat;
import spoticks.ticket_reservation.domain.sport.entity.Sport;
import spoticks.ticket_reservation.domain.stadium.entity.Stadium;
import spoticks.ticket_reservation.domain.team.entity.Team;
import spoticks.ticket_reservation.global.common.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Game extends BaseTimeEntity {

    @Id
    @Column(name = "game_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_id", nullable = false)
    private Sport sport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;

    @Column(nullable = false)
    private LocalDateTime gameStartTime;

    @Column(nullable = false)
    private LocalDateTime timeOnSale;

    @Column(nullable = false)
    private LocalDateTime timeOffSale;

    @OneToMany(mappedBy = "game", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JsonIgnore
    private List<Seat> seats = new ArrayList<>();

    @Builder
    public Game(Stadium stadium, Sport sport, Team homeTeam, Team awayTeam, LocalDateTime gameStartTime) {
        this.stadium = stadium;
        this.sport = sport;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameStartTime = gameStartTime;
        this.timeOnSale = gameStartTime.minusDays(7).withHour(11);
        this.timeOffSale = gameStartTime.minusHours(2);
    }

    public void modifyGameInfo(GameDto.ModifyInfoRes dto) {
        this.stadium = dto.getStadium();
        this.sport = dto.getSport();
        this.homeTeam = dto.getHomeTeam();
        this.awayTeam = dto.getAwayTeam();
        this.gameStartTime = dto.getGameStartTime();
        this.timeOnSale = gameStartTime.minusDays(7).withHour(11).withMinute(0);
        this.timeOffSale = gameStartTime.minusHours(2);
    }

    public void registerSeat(Seat seat) {
        seats.add(seat);
    }

}
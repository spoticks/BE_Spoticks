package spoticks.ticket_reservation.domain.game.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.seat.entity.Seat;
import spoticks.ticket_reservation.domain.sport.entity.Sport;
import spoticks.ticket_reservation.domain.stadium.entity.Stadium;
import spoticks.ticket_reservation.domain.stadium.entity.StadiumType;
import spoticks.ticket_reservation.domain.team.entity.Team;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameDto {

    @Getter
    public static class Req {

        @NotNull
        private String stadiumName;

        @NotNull
        private String sport;

        @NotNull
        private Long homeTeamId;

        @NotNull
        private Long awayTeamId;

        @Valid
        private LocalDateTime gameStartTime;

    }

    @Getter
    public static class ModifyInfoReq {

        private String stadiumName;
        private String sport;
        private long homeTeamId;
        private long awayTeamId;
        private LocalDateTime gameStartTime;

    }

    @Getter
    @Builder
    public static class ModifyInfoRes {

        private Stadium stadium;
        private Sport sport;
        private Team homeTeam;
        private Team awayTeam;
        private LocalDateTime gameStartTime;

    }

    @Getter
    @Builder
    public static class WithSeatList {

        private final SimpleRes game;
        private final List<StadiumType.Section> positionInfo;
        private final List<Seat> seat;

    }

    @Getter
    public static class Res {

        private final long gameId;
        private final String stadium;
        private final String sport;
        private final String homeTeam;
        private final String awayTeam;
        private final LocalDateTime gameStartTime;
        private final LocalDateTime timeOnSale;
        private final LocalDateTime timeOffSale;

        public Res(Game game) {
            this.gameId = game.getId();
            this.stadium = game.getStadium().getStadiumName();
            this.sport = game.getSport().getSportName();
            this.homeTeam = game.getHomeTeam().getTeamName();
            this.awayTeam = game.getAwayTeam().getTeamName();
            this.gameStartTime = game.getGameStartTime();
            this.timeOnSale = game.getTimeOnSale();
            this.timeOffSale = game.getTimeOffSale();
        }

    }

    public static List<Res> toResList(List<Game> games) {
        List<Res> resList = new ArrayList<>();
        for (Game game : games) {
            resList.add(new Res(game));
        }
        return resList;
    }

    @Getter
    public static class SimpleRes {

        private final long gameId;
        private final String homeTeamName;
        private final String awayTeamName;
        private final LocalDateTime gameStartTime;
        private final String stadiumName;
        private final double latitude;
        private final double longitude;

        public SimpleRes(Game game) {
            this.gameId = game.getId();
            this.homeTeamName = game.getHomeTeam().getTeamName();
            this.awayTeamName = game.getAwayTeam().getTeamName();
            this.gameStartTime = game.getGameStartTime();
            this.stadiumName = game.getStadium().getStadiumName();
            this.latitude = game.getStadium().getLatitude();
            this.longitude = game.getStadium().getLongitude();
        }

    }

    public static List<SimpleRes> toSimpleResList(List<Game> games) {
        List<SimpleRes> resList = new ArrayList<>();
        for (Game game : games) {
            resList.add(new SimpleRes(game));
        }
        return resList;
    }

}

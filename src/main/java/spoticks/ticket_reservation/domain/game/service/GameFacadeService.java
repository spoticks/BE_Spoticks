package spoticks.ticket_reservation.domain.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.game.dto.GameDto;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.game.exception.GameTimeOutOfBoundException;
import spoticks.ticket_reservation.domain.game.exception.HomeAndAwaySameException;
import spoticks.ticket_reservation.domain.seat.service.SeatService;
import spoticks.ticket_reservation.domain.sport.entity.Sport;
import spoticks.ticket_reservation.domain.sport.service.SportService;
import spoticks.ticket_reservation.domain.stadium.entity.Stadium;
import spoticks.ticket_reservation.domain.stadium.entity.StadiumType;
import spoticks.ticket_reservation.domain.stadium.service.StadiumService;
import spoticks.ticket_reservation.domain.team.entity.Team;
import spoticks.ticket_reservation.domain.team.service.TeamService;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GameFacadeService {

    private final GameService gameService;
    private final SportService sportService;
    private final StadiumService stadiumService;
    private final TeamService teamService;
    private final SeatService seatService;

    public void createGame(GameDto.Req dto) {
        if (isSameTeam(dto.getHomeTeamId(), dto.getAwayTeamId())) {
            throw new HomeAndAwaySameException();
        }
        Stadium stadium = stadiumService.findByStadiumName(dto.getStadiumName());
        Game newGame = Game.builder()
                .stadium(stadium)
                .sport(sportService.findBySportName(dto.getSport()))
                .homeTeam(teamService.findById(dto.getHomeTeamId()))
                .awayTeam(teamService.findById(dto.getAwayTeamId()))
                .gameStartTime(dto.getGameStartTime())
                .build();

        seatService.registerSeat(newGame, stadium.getStadiumType());
        gameService.saveGame(newGame);

    }

    public GameDto.Res getGame(long gameId) {
        return new GameDto.Res(gameService.findById(gameId));
    }

    public Game getMostPopular() {
        return gameService.pickArbitaryGame();
    }

    public List<GameDto.SimpleRes> getThisWeekGames() {
        return GameDto.toSimpleResList(gameService.findGamesByTimeOffSale());
    }

    public GameDto.WithSeatList getGameWithSeatList(long gameId, String seatPosition) {
        Game game = gameService.findById(gameId);

        if (!gameService.isGameOpen(game)) {
            throw new GameTimeOutOfBoundException();
        }

        List<StadiumType.Section> positionInfo = Arrays.asList(game.getStadium().getStadiumType().getSections());
        if (seatPosition.isEmpty()) {
            seatPosition = positionInfo.get(0).getSeatPosition();
        }

        return GameDto.WithSeatList.builder()
                .game(new GameDto.SimpleRes(game))
                .positionInfo(positionInfo)
                .seat(seatService.findAllByPosition(gameId, seatPosition))
                .build();
    }

    public void modifyGame(long gameId, GameDto.ModifyInfoReq dto) {
        if (isSameTeam(dto.getHomeTeamId(), dto.getAwayTeamId())) {
            throw new HomeAndAwaySameException();
        }

        Game game = gameService.findById(gameId);
        GameDto.ModifyInfoRes res = GameDto.ModifyInfoRes.builder()
                .stadium(stadiumService.findByStadiumName(dto.getStadiumName()))
                .sport(sportService.findBySportName(dto.getSport()))
                .homeTeam(teamService.findById(dto.getHomeTeamId()))
                .awayTeam(teamService.findById(dto.getAwayTeamId()))
                .gameStartTime(dto.getGameStartTime())
                .build();
        game.modifyGameInfo(res);
        gameService.saveGame(game);
    }

    public Page<Game> getGamesBySport(int page, String sportName) {
        Sport sport = sportService.findBySportName(sportName);
        return gameService.findGamesBySport(page, sport);
    }

    public List<GameDto.Res> getGamesByTeam(long teamId) {
        Team team = teamService.findById(teamId);
        return GameDto.toResList(gameService.findGamesByTeam(team));
    }

    public void deleteGame(long gameId) {
        Game game = gameService.findById(gameId);
        gameService.deleteGame(game);
    }

    public boolean isSameTeam(long teamId1, long teamId2) {
        return teamId1 == teamId2;
    }

}

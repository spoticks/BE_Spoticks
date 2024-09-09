package spoticks.ticket_reservation.domain.game.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spoticks.ticket_reservation.domain.game.dto.GameDto;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.game.service.GameFacadeService;
import spoticks.ticket_reservation.global.common.MultiResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameFacadeService gameFacadeService;

    @GetMapping("/games/mostPopular")
    public ResponseEntity getMostPopular() {
        GameDto.Res response = new GameDto.Res(gameFacadeService.getMostPopular());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/games/weekly")
    public ResponseEntity getThisWeekGames() {
        List<GameDto.SimpleRes> gameList = gameFacadeService.getThisWeekGames();
        return new ResponseEntity<>(new MultiResponseDto<>(gameList), HttpStatus.OK);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity getGameWithAssignedSeats(@PathVariable Long gameId, @RequestParam(defaultValue = "") String seatPosition) {
        GameDto.WithSeatList response = gameFacadeService.getGameWithSeatList(gameId, seatPosition);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/games")
    public ResponseEntity getGamesByTeam(@RequestParam long teamId) {
        List<GameDto.Res> gameList = gameFacadeService.getGamesByTeam(teamId);
        return new ResponseEntity<>(new MultiResponseDto<>(gameList), HttpStatus.OK);
    }

    @PostMapping("/admin/games")
    public ResponseEntity createGame(@Valid @RequestBody GameDto.Req dto) {
        gameFacadeService.createGame(dto);
        String response = "경기가 생성되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/admin/games/{gameId}")
    public ResponseEntity updateGame(@PathVariable Long gameId, @Valid @RequestBody GameDto.ModifyInfoReq dto) {
        gameFacadeService.modifyGame(gameId, dto);
        String response = "경기정보가 변경되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admin/games")
    public ResponseEntity getGamesBySport(@RequestParam String sport, @RequestParam(defaultValue = "1") int page) {
        Page<Game> gamePage = gameFacadeService.getGamesBySport(page, sport);
        List<GameDto.Res> gameList = GameDto.toResList(gamePage.getContent());
        return new ResponseEntity<>(new MultiResponseDto<>(gameList, gamePage), HttpStatus.OK);
    }

    @GetMapping("/admin/games/{gameId}")
    public ResponseEntity getGame(@PathVariable Long gameId) {
        GameDto.Res response = gameFacadeService.getGame(gameId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/admin/games/{gameId}")
    public ResponseEntity deleteGame(@PathVariable Long gameId) {
        gameFacadeService.deleteGame(gameId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

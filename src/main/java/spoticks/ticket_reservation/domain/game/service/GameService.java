package spoticks.ticket_reservation.domain.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.game.entity.Game;
import spoticks.ticket_reservation.domain.game.exception.GameNotFoundException;
import spoticks.ticket_reservation.domain.game.exception.GameDeletionTimeExpiredException;
import spoticks.ticket_reservation.domain.game.repository.GameRepository;
import spoticks.ticket_reservation.domain.sport.entity.Sport;
import spoticks.ticket_reservation.domain.team.entity.Team;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final int PAGE_SIZE = 8;

    @Transactional(readOnly = true)
    public Game findById(Long id) {
        return gameRepository.findById(id).orElseThrow(GameNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Optional<Game> pickMainGame() {
        ZonedDateTime now = ZonedDateTime.now();
        return gameRepository.findFirstByTimeOnSaleBeforeAndTimeOffSaleAfter(now, now.plusHours(1));
    }

    @Transactional(readOnly = true)
    public List<Game> findGamesByTimeOffSale() {
        ZonedDateTime now = ZonedDateTime.now();
        return gameRepository.findByTimeOffSaleBetween(now.plusMinutes(30), now.plusDays(6));
    }

    public Page<Game> getAllGames(int page) {
        return gameRepository.findAll(PageRequest.of(
                page - 1, PAGE_SIZE, Sort.by("gameStartTime").ascending()));
    }

    @Transactional(readOnly = true)
    public Page<Game> findGamesBySport(int page, Sport sport, boolean includePastGames) {
        if (includePastGames) {
            return gameRepository.findBySport(sport, PageRequest.of(
                    page - 1, PAGE_SIZE, Sort.by("gameStartTime").ascending()));
        } else {
            ZonedDateTime now = ZonedDateTime.now();
            return gameRepository.findBySportAndTimeOffSaleAfter(sport, now, PageRequest.of(
                    page - 1, PAGE_SIZE, Sort.by("gameStartTime").ascending()));
        }
    }

    @Transactional(readOnly = true)
    public Page<Game> findGamesByTeam(int page, Team team) {
        return gameRepository.findByHomeTeamOrAwayTeamOrderByGameStartTime(
                team, team, PageRequest.of(page - 1, PAGE_SIZE, Sort.by("gameStartTime").ascending()));
    }

    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    public void deleteGame(Game game) {
        if (!isPossibleDeleteGame(game.getTimeOnSale())) {
            throw new GameDeletionTimeExpiredException();
        }
        gameRepository.delete(game);
    }

    public boolean isPossibleDeleteGame(ZonedDateTime timeOnSale) {
        return ZonedDateTime.now().isBefore(timeOnSale.minusHours(2));
    }

    public boolean isGameOpen(Game game) {
        ZonedDateTime now = ZonedDateTime.now();
        return now.isAfter(game.getTimeOnSale()) && now.isBefore(game.getTimeOffSale());
    }

}

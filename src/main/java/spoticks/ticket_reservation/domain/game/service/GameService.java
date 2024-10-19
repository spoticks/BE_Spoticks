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

import java.time.LocalDateTime;
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
        final Optional<Game> game = gameRepository.findById(id);
        game.orElseThrow(GameNotFoundException::new);
        return game.get();
    }

    @Transactional(readOnly = true)
    public Game pickArbitaryGame() {
        LocalDateTime now = LocalDateTime.now();
        final Optional<Game> game = gameRepository.
                findFirstByTimeOnSaleBeforeAndTimeOffSaleAfter(now, now.plusHours(1));
        game.orElseThrow(GameNotFoundException::new);
        return game.get();
    }

    @Transactional(readOnly = true)
    public List<Game> findGamesByTimeOffSale() {
        LocalDateTime now = LocalDateTime.now();
        return gameRepository.findByTimeOffSaleBetween(now.plusMinutes(30), now.plusDays(6));
    }

    public Page<Game> getAllGames(int page) {
        return gameRepository.findAll(PageRequest.of(page - 1, PAGE_SIZE, Sort.by("gameStartTime").ascending()));
    }

    @Transactional(readOnly = true)
    public Page<Game> findGamesBySport(int page, Sport sport) {
        return gameRepository.findBySport(sport, PageRequest.of(page - 1, PAGE_SIZE, Sort.by("gameStartTime").ascending()));
    }

    @Transactional(readOnly = true)
    public List<Game> findGamesByTeam(Team team) {
        return gameRepository.findByHomeTeamOrAwayTeamOrderByGameStartTime(team, team);
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

    public boolean isPossibleDeleteGame(LocalDateTime timeOnSale) {
        return LocalDateTime.now().isBefore(timeOnSale.minusHours(2));
    }

    public boolean isGameOpen(Game game) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(game.getTimeOnSale()) && now.isBefore(game.getTimeOffSale());
    }

}

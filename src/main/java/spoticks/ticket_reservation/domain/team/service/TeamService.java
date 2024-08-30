package spoticks.ticket_reservation.domain.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.team.entity.Team;
import spoticks.ticket_reservation.domain.team.repository.TeamRepository;
import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team findById(Long id) {
        final Optional<Team> team = teamRepository.findById(id);
        team.orElseThrow(() -> new EntityNotFoundException("Team not found"));
        return team.get();
    }

    public Team findByTeamName(String teamName) {
        final Optional<Team> team = teamRepository.findByTeamName(teamName);
        team.orElseThrow(() -> new EntityNotFoundException("Team not found"));
        return team.get();
    }

}

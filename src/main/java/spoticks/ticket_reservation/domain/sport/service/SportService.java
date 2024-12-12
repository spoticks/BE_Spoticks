package spoticks.ticket_reservation.domain.sport.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.sport.entity.Sport;
import spoticks.ticket_reservation.domain.sport.repository.SportRepository;
import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SportService {

    private final SportRepository sportRepository;

    public Sport findById(Long id) {
        final Optional<Sport> sport = sportRepository.findById(id);
        sport.orElseThrow(() -> new EntityNotFoundException("Sport not found"));
        return sport.get();
    }

    public Sport findBySportName(String sportName) {
        final Optional<Sport> sport = sportRepository.findBySportName(sportName);
        sport.orElseThrow(() -> new EntityNotFoundException("Sport not found"));
        return sport.get();
    }

}

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
        return sportRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Sport with id " + id + " not found"));

    }

    public Sport findBySportName(String sportName) {
        return sportRepository.findBySportName(sportName)
            .orElseThrow(() -> new EntityNotFoundException("Sport with name " + sportName + " not found"));
    }

}

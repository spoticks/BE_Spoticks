package spoticks.ticket_reservation.domain.stadium.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.stadium.entity.Stadium;
import spoticks.ticket_reservation.domain.stadium.repository.StadiumRepository;
import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    public Stadium findById(Long id) {
        final Optional<Stadium> stadium = stadiumRepository.findById(id);
        stadium.orElseThrow(() -> new EntityNotFoundException("Stadium not found"));
        return stadium.get();
    }

    public Stadium findByStadiumName(String stadiumName) {
        final Optional<Stadium> stadium = stadiumRepository.findByStadiumName(stadiumName);
        stadium.orElseThrow(() -> new EntityNotFoundException("Stadium not found"));
        return stadium.get();
    }

}

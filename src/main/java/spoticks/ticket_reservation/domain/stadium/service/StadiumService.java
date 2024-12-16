package spoticks.ticket_reservation.domain.stadium.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.stadium.entity.Stadium;
import spoticks.ticket_reservation.domain.stadium.repository.StadiumRepository;
import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    public Stadium findById(Long id) {
        return stadiumRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Stadium with id " + id + " not found"));
    }

    public Stadium findByStadiumName(String stadiumName) {
        return stadiumRepository.findByStadiumName(stadiumName)
            .orElseThrow(() -> new EntityNotFoundException("Stadium with name " + stadiumName + " not found"));
    }

}

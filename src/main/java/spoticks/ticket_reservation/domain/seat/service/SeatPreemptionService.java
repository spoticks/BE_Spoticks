package spoticks.ticket_reservation.domain.seat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spoticks.ticket_reservation.domain.seat.entity.SeatPreemption;
import spoticks.ticket_reservation.domain.seat.exception.SeatPreemptException;
import spoticks.ticket_reservation.domain.seat.repository.SeatPreemptionRepository;
import spoticks.ticket_reservation.global.error.ErrorCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatPreemptionService {

    private final SeatPreemptionRepository seatPreemptionRepository;

    private static final long SEAT_PREEMPTION_DURATION = 480L;

    public void preemptSeat(String seatId, long memberId) {
        Optional<SeatPreemption> existingPreemption = seatPreemptionRepository.findById(seatId);

        if (existingPreemption.isPresent()) {
            throw new SeatPreemptException(ErrorCode.SEAT_ALREADY_SELECTED);
        }

        seatPreemptionRepository.save(SeatPreemption.from(
                seatId, memberId, SEAT_PREEMPTION_DURATION));

    }

    public void verifyPreemptionMember(String seatId, long memberId) {
        SeatPreemption seatPreemption = seatPreemptionRepository.findById(seatId)
                .orElseThrow(() -> new SeatPreemptException(ErrorCode.MISMATCHED_SEAT));
        if (!seatPreemption.getMemberId().equals(memberId)) {
            throw new SeatPreemptException(ErrorCode.MISMATCHED_SEAT);
        }
        cancelPreemption(seatId);
    }

    public boolean isSeatAvailable(String seatId) {
        return seatPreemptionRepository.findById(seatId).isEmpty();
    }

    public void cancelPreemption(String seatId) {
        seatPreemptionRepository.deleteById(seatId);
    }


}

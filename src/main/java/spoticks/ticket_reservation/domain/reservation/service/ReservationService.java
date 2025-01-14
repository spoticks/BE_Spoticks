package spoticks.ticket_reservation.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.member.entity.Member;
import spoticks.ticket_reservation.domain.reservation.entity.Reservation;
import spoticks.ticket_reservation.domain.reservation.entity.ReservationStatus;
import spoticks.ticket_reservation.domain.reservation.exception.ReservationNotFoundException;
import spoticks.ticket_reservation.domain.reservation.repository.ReservationRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(ReservationNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<Reservation> findReservationsByMember(int page, Member member, ReservationStatus status) {
        int PAGE_SIZE = 6;
        return reservationRepository.findAllByMemberAndStatus(member, status, PageRequest.of(page - 1, PAGE_SIZE));
    }

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void deleteReservation(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

}

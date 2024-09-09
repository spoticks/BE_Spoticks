package spoticks.ticket_reservation.domain.reservation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spoticks.ticket_reservation.domain.member.entity.Member;
import spoticks.ticket_reservation.domain.reservation.entity.Reservation;
import spoticks.ticket_reservation.domain.reservation.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByMemberAndStatus(Member member, ReservationStatus status, Pageable pageable);

}

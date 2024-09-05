package spoticks.ticket_reservation.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spoticks.ticket_reservation.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUserName(String userName);

    boolean existsByPhoneNumber(String phoneNumber);

}

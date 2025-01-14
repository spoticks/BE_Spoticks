package spoticks.ticket_reservation.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.member.entity.Member;
import spoticks.ticket_reservation.domain.member.exception.MemberNotFoundException;
import spoticks.ticket_reservation.domain.member.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public boolean isExistedUserName(String userName) {
        return memberRepository.existsByUserName(userName);
    }

    @Transactional(readOnly = true)
    public boolean isExistedPhoneNumber(String phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }
}


package spoticks.ticket_reservation.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.member.entity.Member;
import spoticks.ticket_reservation.domain.member.exception.MemberNotFoundException;
import spoticks.ticket_reservation.domain.member.repository.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        final Optional<Member> member = memberRepository.findById(id);
        member.orElseThrow(MemberNotFoundException::new);
        return member.get();
    }

    @Transactional(readOnly = true)
    public boolean isExistedUserName(String userName) {
        return memberRepository.existsByUserName(userName);
    }

    @Transactional(readOnly = true)
    public boolean isExistedPhoneNumber(String phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional(readOnly = true)
    public boolean isMatchedPassword(String password, Member member) {
        return member.getPassword().equals(password);
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }
}


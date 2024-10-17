package spoticks.ticket_reservation.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.member.dto.MemberDto;
import spoticks.ticket_reservation.domain.member.entity.Member;
import spoticks.ticket_reservation.domain.member.exception.PasswordNotMatchedException;
import spoticks.ticket_reservation.domain.member.exception.PhoneNumberDuplicationException;
import spoticks.ticket_reservation.domain.member.exception.UserNameDuplicationException;
import spoticks.ticket_reservation.domain.team.entity.Team;
import spoticks.ticket_reservation.domain.team.service.TeamService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFacadeService {

    private final MemberService memberService;
    private final TeamService teamService;
    private final PasswordEncoder passwordEncoder;

    public void modifyMemberInfo(Long id, MemberDto.ModifyPhoneReq dto) {
        final Member member = memberService.findById(id);
        member.updateMemberInfo(dto);
        memberService.saveMember(member);
    }

    public void modifyPassword(Long id, MemberDto.ModifyPasswordReq dto) {
        final Member member = memberService.findById(id);
        if (!memberService.isMatchedPassword(dto.getPassword(), member)) {
            throw new PasswordNotMatchedException();
        }
        member.updatePassword(dto.getNewPassword());
        memberService.saveMember(member);
    }

    public void checkUserName(String userName) {
        if (memberService.isExistedUserName(userName)) {
            throw new UserNameDuplicationException(userName);
        }
    }

    public void checkPhoneNumber(String phoneNumber) {
        if (memberService.isExistedPhoneNumber(phoneNumber)) {
            throw new PhoneNumberDuplicationException(phoneNumber);
        }
    }

    public void signUpMember(MemberDto.SignUpReq dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);
        memberService.saveMember(dto.toEntity());
    }

    public MemberDto.Res getUserInfo(Long id) {
        final Member member = memberService.findById(id);
        return new MemberDto.Res(member);
    }

    public void withdrawalMember(Long id, String password) {
        final Member member = memberService.findById(id);
        if (!memberService.isMatchedPassword(password, member)) {
            throw new PasswordNotMatchedException();
        }
        member.withdrawal();
        memberService.saveMember(member);
    }

    public void addMyTeam(Long teamId, Long memberId) {
        final Team team = teamService.findById(teamId);
        final Member member = memberService.findById(memberId);
        member.getTeams().add(team);
        memberService.saveMember(member);
    }

    public void deleteMyTeam(Long teamId, Long memberId) {
        final Team team = teamService.findById(teamId);
        final Member member = memberService.findById(memberId);
        member.getTeams().remove(team);
        memberService.saveMember(member);
    }

    public List<Team> getMyTeams(Long memberId) {
        final Member member = memberService.findById(memberId);
        return member.getTeams().stream().toList();
    }

}

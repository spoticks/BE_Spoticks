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
import spoticks.ticket_reservation.global.auth.AuthorizationUtil;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFacadeService {

    private final MemberService memberService;
    private final TeamService teamService;
    private final PasswordEncoder passwordEncoder;

    public void modifyMemberInfo(MemberDto.ModifyPhoneReq dto) {
        long memberId = AuthorizationUtil.getMemberId();
        checkPhoneNumber(dto.getPhoneNumber());

        final Member member = memberService.findById(memberId);
        member.updateMemberInfo(dto);
        memberService.saveMember(member);
    }

    public void modifyPassword(MemberDto.ModifyPasswordReq dto) {
        long memberId = AuthorizationUtil.getMemberId();
        final Member member = memberService.findById(memberId);

        if (passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            String encodesNewPassword = passwordEncoder.encode(dto.getNewPassword());
            member.updatePassword(encodesNewPassword);
            memberService.saveMember(member);
        } else {
            throw new PasswordNotMatchedException();
        }
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
        checkUserName(dto.getUserName());
        checkPhoneNumber(dto.getPhoneNumber());

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);
        memberService.saveMember(dto.toEntity());
    }

    public MemberDto.Res getUserInfo() {
        long memberId = AuthorizationUtil.getMemberId();
        final Member member = memberService.findById(memberId);
        return new MemberDto.Res(member);
    }

    public void withdrawalMember(String password) {
        long memberId = AuthorizationUtil.getMemberId();
        final Member member = memberService.findById(memberId);

        if (passwordEncoder.matches(password, member.getPassword())) {
            member.withdrawal();
            memberService.saveMember(member);
        } else {
            throw new PasswordNotMatchedException();
        }
    }

    public void addMyTeam(Long teamId) {
        final Team team = teamService.findById(teamId);
        long memberId = AuthorizationUtil.getMemberId();
        final Member member = memberService.findById(memberId);
        member.getTeams().add(team);
        memberService.saveMember(member);
    }

    public void deleteMyTeam(Long teamId) {
        final Team team = teamService.findById(teamId);
        long memberId = AuthorizationUtil.getMemberId();
        final Member member = memberService.findById(memberId);
        member.getTeams().remove(team);
        memberService.saveMember(member);
    }

    public List<Team> getMyTeams() {
        long memberId = AuthorizationUtil.getMemberId();
        final Member member = memberService.findById(memberId);
        return member.getTeams().stream().toList();
    }

}

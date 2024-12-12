package spoticks.ticket_reservation.domain.member.contoller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spoticks.ticket_reservation.domain.member.dto.MemberDto;
import spoticks.ticket_reservation.domain.member.service.MemberFacadeService;
import spoticks.ticket_reservation.domain.team.entity.Team;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacadeService memberFacadeService;

    @PostMapping("/join")
    public ResponseEntity createUser(@Valid @RequestBody MemberDto.SignUpReq dto) {
        memberFacadeService.signUpMember(dto);
        String response = dto.getMemberName() + "님의 회원가입이 완료되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/join/user-name")
    public ResponseEntity checkUserName(@RequestParam String userName) {
        memberFacadeService.checkUserName(userName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/join/phone-number")
    public ResponseEntity checkPhoneNumber(@RequestParam String phoneNumber) {
        memberFacadeService.checkPhoneNumber(phoneNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/members/me")
    public ResponseEntity updateMemberInfo(@RequestBody MemberDto.ModifyPhoneReq dto) {
        memberFacadeService.modifyMemberInfo(dto);
        String response = "회원정보가 정상적으로 변경되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/members/password")
    public ResponseEntity updateMemberPassword(@RequestBody MemberDto.ModifyPasswordReq dto) {
        memberFacadeService.modifyPassword(dto);
        String response = "비밀번호가 정상적으로 변경되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/members/me")
    public ResponseEntity getUserInfo() {
        MemberDto.Res response = memberFacadeService.getUserInfo();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/members")
    public ResponseEntity withdrawalMember(@RequestBody MemberDto.SingleReq dto) {
        memberFacadeService.withdrawalMember(dto.getInput());
        String response = "회원탈퇴가 완료되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/myteam/{teamId}")
    public ResponseEntity addMyTeam(@PathVariable Long teamId) {
        memberFacadeService.addMyTeam(teamId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/myteam/{teamId}")
    public ResponseEntity deleteMyTeam(@PathVariable Long teamId) {
        memberFacadeService.deleteMyTeam(teamId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/myteam")
    public ResponseEntity getMyTeam() {
        List<Team> response = memberFacadeService.getMyTeams();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

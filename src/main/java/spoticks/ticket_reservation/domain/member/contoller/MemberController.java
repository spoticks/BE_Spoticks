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

    @GetMapping("/join/username")
    public ResponseEntity checkUserName(@RequestBody MemberDto.SingleReq dto) {
        memberFacadeService.checkUserName(dto.getInput());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/join/phone")
    public ResponseEntity checkPhoneNumber(@RequestBody MemberDto.SingleReq dto) {
        memberFacadeService.checkPhoneNumber(dto.getInput());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("members/{id}")
    public ResponseEntity updateMemberInfo(@PathVariable Long id , @RequestBody MemberDto.ModifyPhoneReq dto) {
        memberFacadeService.modifyMemberInfo(id, dto);
        String response = "회원정보가 정상적으로 변경되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("members/{id}/password")
    public ResponseEntity updateMemberPassword(@PathVariable Long id, @RequestBody MemberDto.ModifyPasswordReq dto) {
        memberFacadeService.modifyPassword(id, dto);
        String response = "비밀번호가 정상적으로 변경되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("members/{id}")
    public ResponseEntity getUserInfo(@PathVariable Long id) {
        MemberDto.Res response = memberFacadeService.getUserInfo(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("members/{id}")
    public ResponseEntity withdrawalMember(@PathVariable Long id, @RequestBody MemberDto.SingleReq dto) {
        memberFacadeService.withdrawalMember(id, dto.getInput());
        String response = "회원탈퇴가 완료되었습니다.";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/myteam/{teamId}")
    public ResponseEntity addMyTeam(@PathVariable Long teamId, @RequestParam Long memberId) {
        memberFacadeService.addMyTeam(teamId, memberId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/myteam/{teamId}")
    public ResponseEntity deleteMyTeam(@PathVariable Long teamId, @RequestParam Long memberId) {
        memberFacadeService.deleteMyTeam(teamId, memberId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/myteam")
    public ResponseEntity getMyTeam(@RequestParam Long memberId) {
        List<Team> response = memberFacadeService.getMyTeams(memberId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

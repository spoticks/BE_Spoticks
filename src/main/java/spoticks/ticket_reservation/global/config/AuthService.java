package spoticks.ticket_reservation.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.domain.member.entity.Member;
import spoticks.ticket_reservation.domain.member.service.MemberService;
import spoticks.ticket_reservation.global.config.jwt.JwtTokenProvider;
import spoticks.ticket_reservation.global.login.AuthResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public String createToken(Authentication authentication) {
        return jwtTokenProvider.generateToken(authentication);
    }

    public AuthResponse generateAuthResponse(String token) {
        String userName = jwtTokenProvider.getUsernameFromJWT(token);
        Member member = memberService.findByUsername(userName);
        return AuthResponse.builder()
                .memberId(member.getId()).memberName(member.getMemberName()).token(token)
                .build();
    }

}

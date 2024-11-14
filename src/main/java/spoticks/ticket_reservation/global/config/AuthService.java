package spoticks.ticket_reservation.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.global.config.jwt.JwtTokenizer;
import spoticks.ticket_reservation.global.login.AuthResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenizer jwtTokenizer;

    public AuthResponse createToken(Authentication authentication) {
        return new AuthResponse(jwtTokenizer.generateToken(authentication));
    }

}

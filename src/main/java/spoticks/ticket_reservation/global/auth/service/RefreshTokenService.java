package spoticks.ticket_reservation.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spoticks.ticket_reservation.global.auth.CustomUserDetails;
import spoticks.ticket_reservation.global.auth.entity.RefreshToken;
import spoticks.ticket_reservation.global.auth.exception.TokenCheckFailException;
import spoticks.ticket_reservation.global.auth.repository.RefreshTokenRepository;
import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.security.JwtTokenizer;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken saveRefreshToken(CustomUserDetails user, long expirationTime) {
        return refreshTokenRepository.save(RefreshToken.from(
            user.getUsername(), jwtTokenizer.generateToken(user, expirationTime), expirationTime));
    }

    public RefreshToken findRefreshTokenById(String username) {
        return refreshTokenRepository.findById(username)
                .orElseThrow(() -> new TokenCheckFailException(ErrorCode.TOKEN_NOT_FOUND));
    }

    public void deleteRefreshToken(String username) {
        refreshTokenRepository.deleteById(username);
    }
}

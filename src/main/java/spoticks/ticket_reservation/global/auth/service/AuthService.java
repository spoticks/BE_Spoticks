package spoticks.ticket_reservation.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.global.auth.AuthResponse;
import spoticks.ticket_reservation.global.auth.CustomUserDetails;
import spoticks.ticket_reservation.global.auth.CustomUserDetailsService;
import spoticks.ticket_reservation.global.auth.entity.LogoutAccessToken;
import spoticks.ticket_reservation.global.auth.entity.RefreshToken;
import spoticks.ticket_reservation.global.auth.exception.TokenCheckFailException;
import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.security.JwtTokenizer;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;
    private final LogoutAccessTokenService logoutAccessTokenService;
    private final CustomUserDetailsService userService;


    public AuthResponse login(CustomUserDetails user) {
        String accessToken = jwtTokenizer.generateToken(user, JwtTokenizer.ACCESS_TOKEN_EXPIRE);
        RefreshToken refreshToken = refreshTokenService.saveRefreshToken(user, JwtTokenizer.REFRESH_TOKEN_EXPIRE);
        jwtTokenizer.setRefreshTokenAtCookie(refreshToken);
        return new AuthResponse(accessToken);
    }

    public void logout(String accessToken) {
        accessToken = jwtTokenizer.resolveToken(accessToken);
        String username = jwtTokenizer.parseClaims(accessToken).getSubject();
        long remainTime = jwtTokenizer.getRemainTime(accessToken);
        refreshTokenService.deleteRefreshToken(username);
        logoutAccessTokenService.saveLogoutAccessToken(LogoutAccessToken.from(username, accessToken, remainTime));
    }

    public AuthResponse reissueAccessToken(String refreshToken, String username) {
        RefreshToken redisToken = refreshTokenService.findRefreshTokenById(username);

        if (!refreshToken.equals(redisToken.getRefreshToken())) {
            throw new TokenCheckFailException(ErrorCode.MISMATCH_TOKEN);
        }

        String accessToken = jwtTokenizer.generateToken(
                userService.loadUserByUsername(username), JwtTokenizer.ACCESS_TOKEN_EXPIRE);
        return new AuthResponse(accessToken);
    }

}

package spoticks.ticket_reservation.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spoticks.ticket_reservation.global.auth.dto.AuthResponse;
import spoticks.ticket_reservation.global.auth.entity.CustomUserDetails;
import spoticks.ticket_reservation.global.auth.entity.LogoutAccessToken;
import spoticks.ticket_reservation.global.auth.entity.RefreshToken;
import spoticks.ticket_reservation.global.auth.exception.TokenCheckFailException;
import spoticks.ticket_reservation.global.config.JwtConfig;
import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.security.JwtTokenizer;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenizer jwtTokenizer;
    private final JwtConfig jwtConfig;
    private final RefreshTokenService refreshTokenService;
    private final LogoutAccessTokenService logoutAccessTokenService;
    private final CustomUserDetailsService userService;

    public AuthResponse login(CustomUserDetails user) {
        String accessToken = jwtTokenizer.generateToken(user, jwtConfig.getAccessTokenExpire());
        RefreshToken refreshToken = refreshTokenService.saveRefreshToken(user, jwtConfig.getRefreshTokenExpire());
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
                userService.loadUserByUsername(username), jwtConfig.getAccessTokenExpire());
        return new AuthResponse(accessToken);
    }

}

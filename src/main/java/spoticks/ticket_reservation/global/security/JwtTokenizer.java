package spoticks.ticket_reservation.global.security;

import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import spoticks.ticket_reservation.global.auth.CustomUserDetails;
import spoticks.ticket_reservation.global.auth.entity.RefreshToken;
import spoticks.ticket_reservation.global.auth.service.LogoutAccessTokenService;
import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.JwtAuthenticationException;

import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenizer {

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public static final long ACCESS_TOKEN_EXPIRE = 1000L * 60 * 2; // 5분
    public static final long REFRESH_TOKEN_EXPIRE = 1000L * 60 * 3; // 7분

    private final LogoutAccessTokenService logoutAccessTokenService;

    public String generateToken(CustomUserDetails userPrincipal, long expirationTime) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("memberId", userPrincipal.getId());
        claims.put("memberName", userPrincipal.getMemberName());
        claims.put("authorities", userPrincipal.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, encodeBase64(JWT_SECRET))
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(encodeBase64(JWT_SECRET))
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean verifyToken(String authToken) {
        try {
            validateToken(authToken);

            if (checkLogout(authToken)) {
                throwJwtException(ErrorCode.TOKEN_EXPIRED);
            }

            return true;
        } catch (JwtException ex) {
            handleJwtException(ex);
        }

        return false;
    }

    public String resolveToken(String token) {
        if(token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    public long getRemainTime(String authToken) {
        Date expiration = parseClaims(authToken).getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

    public void setRefreshTokenAtCookie(RefreshToken refreshToken) {
        Cookie cookie = new Cookie("RefreshToken", refreshToken.getRefreshToken());
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); HTTPS 적용
        cookie.setMaxAge(refreshToken.getExpiration().intValue());
        cookie.setAttribute("SameSite", "Strict");
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getResponse();
        Objects.requireNonNull(response).addCookie(cookie);
    }

    private void handleJwtException(JwtException ex) {
        if (ex instanceof SignatureException) {
            throwJwtException(ErrorCode.INVALID_SIGNATURE);
        } else if (ex instanceof MalformedJwtException) {
            throwJwtException(ErrorCode.MALFORMED_TOKEN);
        } else if (ex instanceof ExpiredJwtException) {
            throwJwtException(ErrorCode.TOKEN_EXPIRED);
        } else {
            throw new JwtAuthenticationException(ErrorCode.UNAUTHORIZED);
        }
    }

    private void throwJwtException(ErrorCode errorCode) {
        throw new JwtAuthenticationException(errorCode);
    }

    private String encodeBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    private void validateToken(String authToken) {
        Jwts.parser().setSigningKey(encodeBase64(JWT_SECRET)).parseClaimsJws(authToken);
    }

    private boolean checkLogout(String accessToken) {
        return logoutAccessTokenService.existsLogoutAccessTokenById(accessToken);
    }

}
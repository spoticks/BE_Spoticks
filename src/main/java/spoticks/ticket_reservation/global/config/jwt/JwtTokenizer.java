package spoticks.ticket_reservation.global.config.jwt;

import io.jsonwebtoken.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import spoticks.ticket_reservation.global.config.CustomUserDetails;
import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenizer {

    @Value("${jwt.secret}")
    @Getter
    private String JWT_SECRET;

    private final long JWT_EXPIRATION = 1800000L; // 30분

    public String generateToken(Authentication authentication) {
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();

        claims.put("memberId", userPrincipal.getId());
        claims.put("memberName", userPrincipal.getMemberName());
        claims.put("authorities", userPrincipal.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, encodeBase64(JWT_SECRET))
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(encodeBase64(JWT_SECRET))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(encodeBase64(JWT_SECRET)).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            throw new InvalidValueException("Invalid JWT signature", ErrorCode.UNAUTHORIZED);
        } catch (MalformedJwtException ex) {
            throw new InvalidValueException("JWT token format incorrect", ErrorCode.UNAUTHORIZED);
        } catch (ExpiredJwtException ex) {
            throw new InvalidValueException("JWT already expired", ErrorCode.UNAUTHORIZED);
        }
    }

    // JWT Base64 인코딩
    public String encodeBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

}
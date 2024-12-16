package spoticks.ticket_reservation.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    @Value("${jwt.access-expire}")
    private String accessTokenExpireStr;

    @Value("${jwt.refresh-expire}")
    private String refreshTokenExpireStr;

    public long getAccessTokenExpire() {
        return Long.parseLong(accessTokenExpireStr);
    }

    public long getRefreshTokenExpire() {
        return Long.parseLong(refreshTokenExpireStr);
    }

}

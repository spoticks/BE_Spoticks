package spoticks.ticket_reservation.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    @Value("${jwt.access-expire}")
    private String accessTokenExpireStr;

    @Value("${jwt.refresh-expire}")
    private String refreshTokenExpireStr;

    @Value("${jwt.refresh-reissue}")
    private String refreshTokenReissueStr;

    public long getAccessTokenExpire() {
        return Long.parseLong(accessTokenExpireStr);
    }

    public long getRefreshTokenExpire() {
        return Long.parseLong(refreshTokenExpireStr);
    }

    public long getRefreshTokenReissue() {
        return Long.parseLong(refreshTokenReissueStr);
    }

}

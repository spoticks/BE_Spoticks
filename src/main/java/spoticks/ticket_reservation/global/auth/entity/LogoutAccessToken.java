package spoticks.ticket_reservation.global.auth.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Builder
@RedisHash("logoutAccess")
public class LogoutAccessToken {

    @Id
    private String id;

    private String username;

    @TimeToLive
    private Long expiration;

    public static LogoutAccessToken from(String username, String accessToken, Long expirationTime) {
        return LogoutAccessToken.builder()
                .id(accessToken)
                .username(username)
                .expiration(expirationTime / 1000)
                .build();
    }

}

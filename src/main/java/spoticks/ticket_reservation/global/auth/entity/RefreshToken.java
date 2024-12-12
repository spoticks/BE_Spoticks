package spoticks.ticket_reservation.global.auth.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Builder
@RedisHash("refresh")
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public static RefreshToken from(String username, String refreshToken, Long expirationTime) {
        return RefreshToken.builder()
                .id(username)
                .refreshToken(refreshToken)
                .expiration(expirationTime / 1000)
                .build();
    }

}

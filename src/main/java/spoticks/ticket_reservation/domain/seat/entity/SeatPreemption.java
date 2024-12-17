package spoticks.ticket_reservation.domain.seat.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Builder
@RedisHash("seat")
public class SeatPreemption {

    @Id
    private String seatId;

    private Long memberId;

    @TimeToLive
    private Long expiration;

    public static SeatPreemption from(String seatId, Long memberId, Long expiration) {
        return SeatPreemption.builder()
                .seatId(seatId)
                .memberId(memberId)
                .expiration(expiration)
                .build();
    }
}

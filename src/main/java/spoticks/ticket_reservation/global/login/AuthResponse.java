package spoticks.ticket_reservation.global.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    private long memberId;
    private String memberName;
    private String token;

}

package spoticks.ticket_reservation.global.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import spoticks.ticket_reservation.global.config.CustomUserDetails;

public class AuthorizationUtil {

    private AuthorizationUtil() {
        throw new AssertionError();
    }

    public static Long getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }

}

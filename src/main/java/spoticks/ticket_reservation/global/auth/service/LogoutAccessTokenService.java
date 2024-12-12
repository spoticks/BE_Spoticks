package spoticks.ticket_reservation.global.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spoticks.ticket_reservation.global.auth.entity.LogoutAccessToken;
import spoticks.ticket_reservation.global.auth.repository.LogoutAccessTokenRepository;

@Service
@RequiredArgsConstructor
public class LogoutAccessTokenService {

    private final LogoutAccessTokenRepository logoutAccessTokenRepository;

    public void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken) {
        logoutAccessTokenRepository.save(logoutAccessToken);
    }

    public boolean existsLogoutAccessTokenById(String token) {
        return logoutAccessTokenRepository.existsById(token);
    }
}

package spoticks.ticket_reservation.global.auth.repository;

import org.springframework.data.repository.CrudRepository;
import spoticks.ticket_reservation.global.auth.entity.LogoutAccessToken;

public interface LogoutAccessTokenRepository extends CrudRepository<LogoutAccessToken, String> {
}

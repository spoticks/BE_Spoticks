package spoticks.ticket_reservation.global.auth.repository;

import org.springframework.data.repository.CrudRepository;
import spoticks.ticket_reservation.global.auth.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}

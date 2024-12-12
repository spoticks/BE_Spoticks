package spoticks.ticket_reservation.domain.game.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.BusinessException;

public class GameDeletionTimeExpiredException extends BusinessException {

    public GameDeletionTimeExpiredException() {
        super("Game deletion time expired", ErrorCode.GAME_DELETION_FAILED);
    }

}

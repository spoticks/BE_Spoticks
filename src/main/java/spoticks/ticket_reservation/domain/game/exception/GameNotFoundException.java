package spoticks.ticket_reservation.domain.game.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

public class GameNotFoundException extends EntityNotFoundException {

    public GameNotFoundException() {
        super("Game not found", ErrorCode.GAME_NOT_FOUND);
    }

}

package spoticks.ticket_reservation.domain.game.exception;

import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

public class GameNotFoundException extends EntityNotFoundException {

    public GameNotFoundException() {
        super("Game not found");
    }

}

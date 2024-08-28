package spoticks.ticket_reservation.domain.game.exception;

import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

public class GameSaleAlreadyStartedException extends InvalidValueException {

    public GameSaleAlreadyStartedException() {
        super("Game sale already started");
    }

}

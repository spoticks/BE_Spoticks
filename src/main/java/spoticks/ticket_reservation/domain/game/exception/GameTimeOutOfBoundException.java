package spoticks.ticket_reservation.domain.game.exception;

import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

public class GameTimeOutOfBoundException extends InvalidValueException {
    public GameTimeOutOfBoundException() {
        super("Time is out of bound");
    }
}

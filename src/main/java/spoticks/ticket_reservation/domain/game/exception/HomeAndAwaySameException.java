package spoticks.ticket_reservation.domain.game.exception;

import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

public class HomeAndAwaySameException extends InvalidValueException {

    public HomeAndAwaySameException() {
        super("Home and Away can not be the same");
    }

}

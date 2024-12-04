package spoticks.ticket_reservation.domain.game.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.BusinessException;

public class GameTimeOutOfBoundException extends BusinessException {
    public GameTimeOutOfBoundException() {
        super("Time to access this game has ended", ErrorCode.RESERVATION_NOT_AVAILABLE);
    }
}

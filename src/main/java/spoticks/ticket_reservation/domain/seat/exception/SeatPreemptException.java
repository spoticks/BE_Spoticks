package spoticks.ticket_reservation.domain.seat.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

public class SeatPreemptException extends InvalidValueException {

    public SeatPreemptException(ErrorCode errorCode) {
        super(errorCode);
    }

}

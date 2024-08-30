package spoticks.ticket_reservation.domain.seat.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

public class SeatExpiredException extends InvalidValueException {

    public SeatExpiredException() {
        super("Seat preemption time has expired.", ErrorCode.SEAT_TIMEOUT);
    }

}

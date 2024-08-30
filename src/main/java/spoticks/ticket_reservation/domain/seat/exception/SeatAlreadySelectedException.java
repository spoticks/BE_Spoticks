package spoticks.ticket_reservation.domain.seat.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

public class SeatAlreadySelectedException extends InvalidValueException {

    public SeatAlreadySelectedException() {
        super("Seat already preempted", ErrorCode.SEAT_ALREADY_SELECTED);
    }

}

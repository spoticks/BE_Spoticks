package spoticks.ticket_reservation.domain.reservation.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

public class CancellationPeriodExpiredException extends InvalidValueException {

    public CancellationPeriodExpiredException() {
        super("The cancellation period has expired.", ErrorCode.RESERVATION_EXPIRE);
    }

}

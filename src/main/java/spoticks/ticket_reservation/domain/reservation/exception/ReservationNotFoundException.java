package spoticks.ticket_reservation.domain.reservation.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

public class ReservationNotFoundException extends EntityNotFoundException {

    public ReservationNotFoundException() {
        super(ErrorCode.RESERVATION_NOT_FOUND);
    }

}

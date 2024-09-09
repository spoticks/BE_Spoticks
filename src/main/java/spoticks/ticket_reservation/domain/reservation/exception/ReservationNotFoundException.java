package spoticks.ticket_reservation.domain.reservation.exception;

import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

public class ReservationNotFoundException extends EntityNotFoundException {

    public ReservationNotFoundException() {
        super("Reservation not found");
    }

}

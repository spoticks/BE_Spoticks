package spoticks.ticket_reservation.domain.seat.exception;

import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

public class SeatNotFoundException extends EntityNotFoundException {

    public SeatNotFoundException() {
        super("Seat not found");
    }

}

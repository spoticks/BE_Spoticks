package spoticks.ticket_reservation.domain.member.exception;

import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

public class PasswordNotMatchedException extends InvalidValueException {

    public PasswordNotMatchedException() {
        super("Password does not matched");
    }
}

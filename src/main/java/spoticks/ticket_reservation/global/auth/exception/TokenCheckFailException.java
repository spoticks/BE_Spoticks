package spoticks.ticket_reservation.global.auth.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

public class TokenCheckFailException extends EntityNotFoundException {

    public TokenCheckFailException(ErrorCode errorCode) {
        super("Token check failed", errorCode);
    }

}

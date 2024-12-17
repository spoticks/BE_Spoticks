package spoticks.ticket_reservation.global.error.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}

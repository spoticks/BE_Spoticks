package spoticks.ticket_reservation.domain.member.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

public class UserNameDuplicationException extends InvalidValueException {

    public UserNameDuplicationException(String userName) {
        super(String.format("Username %s already exists", userName), ErrorCode.USERNAME_DUPLICATION);
    }

}

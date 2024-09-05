package spoticks.ticket_reservation.domain.member.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.InvalidValueException;

public class PhoneNumberDuplicationException extends InvalidValueException {

    public PhoneNumberDuplicationException(String phoneNumber) {
        super(String.format("Phone number %s already exists", phoneNumber), ErrorCode.PHONE_DUPLICATION);
    }

}

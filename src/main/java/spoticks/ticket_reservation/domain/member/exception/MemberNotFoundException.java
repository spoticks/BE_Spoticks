package spoticks.ticket_reservation.domain.member.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException() {
        super("Member not found", ErrorCode.MEMBER_NOT_FOUND);
    }

}

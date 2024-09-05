package spoticks.ticket_reservation.domain.member.exception;

import spoticks.ticket_reservation.global.error.exception.EntityNotFoundException;

public class UserNameNotFound extends EntityNotFoundException {

    public UserNameNotFound(String userName) {
        super(userName + " is not found");
    }

}

package spoticks.ticket_reservation.global.error.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }

}

package spoticks.ticket_reservation.domain.game.exception;

import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.exception.BusinessException;

public class HomeAndAwaySameException extends BusinessException {

    public HomeAndAwaySameException() {
        super("Home and Away can not be the same", ErrorCode.GAME_SAVE_FAILED);
    }

}

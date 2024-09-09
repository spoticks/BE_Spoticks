package spoticks.ticket_reservation.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    INVALID_TYPE_VALUE(400, "Invalid Type Value"),
    ENTITY_NOT_FOUND(404, " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    HANDLE_ACCESS_DENIED(403, "Access is Denied"),

    // Member
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    EMAIL_DUPLICATION(400, "Email is Duplication"),
    PHONE_DUPLICATION(400, "Phone Number is Duplication"),
    USERNAME_DUPLICATION(400, "LoginId is Duplication"),

    // Game
    GAME_DELETION_FAILED(400, "Game Deletion Failed"),
    GAME_SAVE_FAILED(400, "Game Save Failed"),
    GAME_NOT_FOUND(404, "Game Not Found"),

    // Reservation
    RESERVATION_EXPIRE(400, "Reservation already expired"),

    // Seat
    SEAT_ALREADY_SELECTED(400, "Seat already selected"),
    SEAT_TIMEOUT(400, "Seat occupancy time has ended"),
    ;

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

}

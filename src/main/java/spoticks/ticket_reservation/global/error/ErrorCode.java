package spoticks.ticket_reservation.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "Invalid Input Value"),
    INVALID_TYPE_VALUE(400, "Invalid Type Value"),
    UNAUTHORIZED(401, "Invalid username or password"),
    ENTITY_NOT_FOUND(404, "Entity Not Found"),
    PAGE_NOT_FOUND(404, "Requested endpoint does not exist"),
    INTERNAL_SERVER_ERROR(500, "Server Error"),
    HANDLE_ACCESS_DENIED(403, "Access is Denied"),

    // JWT
    INVALID_SIGNATURE(401, "Invalid JWT signature"),
    MALFORMED_TOKEN(401, "JWT Token format incorrect"),
    TOKEN_EXPIRED(401, "JWT Token expired"),
    UNSUPPORTED_TOKEN(401, "Unsupported Token"),
    EMPTY_TOKEN(401, "JWT Token is empty"),
    TOKEN_NOT_FOUND(404, "JWT Token does not exist"),
    MISMATCH_TOKEN(401, "JWT Token does not match"),

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
    RESERVATION_NOT_AVAILABLE(400, "Reservation is not available at this time"),
    RESERVATION_NOT_FOUND(404, "Reservation Not Found"),

    // Seat
    SEAT_ALREADY_SELECTED(403, "Seat already selected"),
    SEAT_TIMEOUT(400, "Seat occupancy time has ended"),
    SEAT_NOT_FOUND(404, "Seat Not Found"),
    MISMATCHED_SEAT(403, "Seat does not match"),
    ;

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

}

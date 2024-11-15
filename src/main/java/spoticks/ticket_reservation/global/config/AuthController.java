package spoticks.ticket_reservation.global.config;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.ErrorResponse;
import spoticks.ticket_reservation.global.login.AuthRequest;
import spoticks.ticket_reservation.global.login.AuthResponse;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );

            AuthResponse response = authService.createToken(authentication);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(ErrorResponse.of(ErrorCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }
    }

}

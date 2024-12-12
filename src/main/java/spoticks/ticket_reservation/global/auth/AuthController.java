package spoticks.ticket_reservation.global.auth;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import spoticks.ticket_reservation.global.auth.service.AuthService;
import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.ErrorResponse;


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

            AuthResponse response = authService.login((CustomUserDetails) authentication.getPrincipal());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(ErrorResponse.of(ErrorCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/reissue/{username}")
    public ResponseEntity reissue(@CookieValue String RefreshToken,
                                  @PathVariable String username) {
        AuthResponse response = authService.reissueAccessToken(RefreshToken, username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String accessToken) {
        authService.logout(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

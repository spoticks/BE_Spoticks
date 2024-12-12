package spoticks.ticket_reservation.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import spoticks.ticket_reservation.global.error.ErrorCode;
import spoticks.ticket_reservation.global.error.ErrorResponse;

import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ErrorCode errorCode = (ErrorCode) request.getAttribute("errorCode");

        if (errorCode == null) {
            errorCode = ErrorCode.PAGE_NOT_FOUND;
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if (errorCode == ErrorCode.HANDLE_ACCESS_DENIED) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}

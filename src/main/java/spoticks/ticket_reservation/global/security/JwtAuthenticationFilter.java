package spoticks.ticket_reservation.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spoticks.ticket_reservation.global.auth.entity.CustomUserDetails;
import spoticks.ticket_reservation.global.auth.service.CustomUserDetailsService;
import spoticks.ticket_reservation.global.error.exception.AccessDeniedException;
import spoticks.ticket_reservation.global.error.exception.JwtAuthenticationException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isPermitAllPath(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = getJwtFromRequest(request);

            if (jwtTokenizer.verifyToken(jwt)) {
                String username = jwtTokenizer.parseClaims(jwt).getSubject();
                CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (request.getRequestURI().startsWith("/admin")) {
                    if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                        throw new AccessDeniedException("You do not have permission to access this resource");
                    }
                }

                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtAuthenticationException | AccessDeniedException ex) {
            if (ex instanceof JwtAuthenticationException) {
                request.setAttribute("errorCode", ((JwtAuthenticationException) ex).getErrorCode());
            } else {
                request.setAttribute("errorCode", ((AccessDeniedException) ex).getErrorCode());
            }

            SecurityContextHolder.clearContext();
            throw ex;
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isPermitAllPath(String requestURI) {
        return requestURI.startsWith("/auth/login") ||
                requestURI.startsWith("/auth/reissue") ||
                requestURI.startsWith("/join") ||
                requestURI.startsWith("/games/mostPopular") ||
                requestURI.startsWith("/games/weekly") ||
                requestURI.startsWith("/games/sports") ||
                requestURI.startsWith("/teams");
    }

}


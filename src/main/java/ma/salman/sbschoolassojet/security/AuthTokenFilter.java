package ma.salman.sbschoolassojet.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Starting filter execution for request to: {}", request.getRequestURI());
        try {
            String jwt = parseJwt(request);
            logger.debug("JWT Token extracted from request: {}", jwt != null ? jwt.substring(0, Math.min(10, jwt.length())) + "..." : "null");

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                logger.debug("JWT Token is valid");

                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                logger.debug("Username extracted from JWT: {}", username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                logger.debug("User details loaded: username={}, authorities={}",
                        userDetails.getUsername(),
                        userDetails.getAuthorities());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.debug("Authentication object created with authorities: {}", authentication.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Authentication object set in SecurityContextHolder");
            } else {
                logger.debug("No valid JWT token found in request");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
            logger.debug("Authentication error details", e);
        }

        logger.debug("Continuing filter chain");
        filterChain.doFilter(request, response);
        logger.debug("Filter chain execution completed for request: {}", request.getRequestURI());
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        logger.debug("Authorization header: {}", headerAuth != null ?
                headerAuth.substring(0, Math.min(15, headerAuth.length())) + "..." : "null");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            logger.debug("Bearer token found in Authorization header");
            return headerAuth.substring(7);
        }

        logger.debug("No Bearer token found in request headers");
        return null;
    }
}
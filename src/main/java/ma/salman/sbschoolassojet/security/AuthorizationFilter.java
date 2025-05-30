package ma.salman.sbschoolassojet.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final String[] PUBLIC_URLS = {
            "/api/auth/",
            "/api/test/",
            "/api/enseignants/",
            "/api/parents/",
            "/h2-console/",
            "/swagger-ui/",
            "/v3/api-docs/",
            "/swagger-resources/",
            "/webjars/"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getServletPath();

        // Vérifier si l'URL est publique
        if (isPublicPath(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = authorizationHeader.substring(7);
            Algorithm algorithm = Algorithm.HMAC256("secretKey");
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

            String username = decodedJWT.getSubject();

            // Extraire les rôles du JWT
            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            Collection<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // Créer un objet UserDetailsImpl pour inclure l'ID et autres infos
            UserDetailsImpl userDetails = new UserDetailsImpl(
                    decodedJWT.getClaim("id").asLong(),
                    username,
                    decodedJWT.getClaim("email").asString(),
                    "", // password vide car déjà authentifié
                    authorities
            );

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (TokenExpiredException tokenExpiredException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expiré");
            return;
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Token invalide");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return Arrays.stream(PUBLIC_URLS)
                .anyMatch(path::startsWith);
    }
}
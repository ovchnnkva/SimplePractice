package ru.company.understandablepractice.security;

import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.company.understandablepractice.model.PersonCredentials;
import ru.company.understandablepractice.model.UserCredentials;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.security.services.PersonCredentialsService;
import ru.company.understandablepractice.security.services.UserCredentialsService;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final UserCredentialsService userCredentialsService;
    private final PersonCredentialsService personCredentialsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Получаем токен из заголовка
        var authHeader = request.getHeader(HEADER_NAME);
        log.info(authHeader);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Обрезаем префикс и получаем имя пользователя из токена
        var jwt = authHeader.substring(BEARER_PREFIX.length());
        long userId;

        userId = jwtService.extractUserId(jwt, JwtType.ACCESS);


        if (userId != 0 && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("auth user with id {}", userId);
            Optional<UserCredentials> userDetails = userCredentialsService.findByUserId(userId);
            Optional<PersonCredentials> personCredentials = Optional.empty();
            if(userDetails.isEmpty()) {
                personCredentials = personCredentialsService.findByPersonId(userId);
            }

            // Если токен валиден, то аутентифицируем пользователя
            if (userDetails.isPresent() && jwtService.isTokenValid(jwt,JwtType.ACCESS, userDetails.get())) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails.get().getUsername(),
                        null,
                        userDetails.get().getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            } else if (personCredentials.isPresent() && jwtService.isTokenValid(jwt,JwtType.ACCESS, personCredentials.get())) {
                var check  = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        personCredentials.get().getUsername(),
                        null,
                        personCredentials.get().getAuthorities()
                );
                log.info("personCredentials.get().getAuthorities() {}", personCredentials.get().getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }

        filterChain.doFilter(request, response);
    }
}


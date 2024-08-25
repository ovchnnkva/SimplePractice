
package ru.company.understandablepractice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.company.understandablepractice.service.UserCredentialsService;

/**
 *
 * @author mish
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserCredentialsService userCredentialsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        log.info("create user");
        var user = userCredentialsService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var access = jwtService.generateToken(user, JwtType.ACCESS);
        var refresh = jwtService.generateToken(user, JwtType.REFRESH);
        return new JwtAuthenticationResponse(access, refresh);
    }
}

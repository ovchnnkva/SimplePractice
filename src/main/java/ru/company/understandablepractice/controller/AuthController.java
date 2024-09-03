package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.UserCredentials;
import ru.company.understandablepractice.security.*;
import ru.company.understandablepractice.service.UserCredentialsService;
import ru.company.understandablepractice.service.UserService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserCredentialsService userCredentialsService;
    private final UserService userService;

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest request) {
        return new ResponseEntity<>(authenticationService.signIn(request), HttpStatus.OK);
    }

    @Operation(summary = "Обновление access токена")
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody JwtRefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        long userId = jwtService.extractUserId(refreshToken, JwtType.REFRESH);
        Optional<User> user = userService.getById(userId);
        ResponseEntity<JwtAuthenticationResponse> response;
        if (user.isEmpty() || !jwtService.isTokenValid(refreshToken, JwtType.REFRESH, user.get().getUserCredentials())) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        }
        UserCredentials userCredentials = user.get().getUserCredentials();
        JwtAuthenticationResponse jwtResponse = JwtAuthenticationResponse.builder()
                .refresh(jwtService.generateToken(userCredentials, JwtType.REFRESH))
                .access(jwtService.generateToken(userCredentials, JwtType.ACCESS))
                .build();

        response = new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        return response;
    }
}
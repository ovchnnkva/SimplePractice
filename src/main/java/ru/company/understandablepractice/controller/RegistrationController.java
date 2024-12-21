package ru.company.understandablepractice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.understandablepractice.security.SignInRequest;
import ru.company.understandablepractice.security.UserAlreadyExists;
import ru.company.understandablepractice.security.services.SignInService;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
@Tag(name = "Регистрация")
public class RegistrationController {

    private final SignInService signInService;

    @PostMapping("/sign-up")
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<?> signUp(@RequestBody SignInRequest signInRequest){
        ResponseEntity<String> response = null;
        try {
            signInService.signIn(signInRequest);
            response = new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (UserAlreadyExists e) {
            response = new ResponseEntity<>("Username already exists",HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        return response;
    }


}

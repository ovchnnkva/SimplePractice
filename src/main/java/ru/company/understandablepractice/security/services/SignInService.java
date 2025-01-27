package ru.company.understandablepractice.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.UserCredentials;
import ru.company.understandablepractice.security.SignInRequest;
import ru.company.understandablepractice.security.UserAlreadyExists;
import ru.company.understandablepractice.service.UserService;

@Service
public class SignInService {
    @Autowired
    private UserCredentialsService userCredentialsService;
    @Autowired
    private UserService userService;

    public void signIn(SignInRequest signInRequest) throws Exception {
        var username = signInRequest.getUsername();
        var fullName = String.format("%s %s %s", signInRequest.getLastName(), signInRequest.getFirstName(), signInRequest.getSecondName());

        if(userCredentialsService.isUserCredentialsAlreadyExists(username)){
            throw new UserAlreadyExists("User with such username already exists ");
        }
        var user = createUser(signInRequest, fullName);

        userService.create(user);
    }

    private User createUser(SignInRequest signInRequest, String fullName){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        var user = new User();
        user.setFirstName(signInRequest.getFirstName());
        user.setSecondName(signInRequest.getSecondName());
        user.setLastName(signInRequest.getLastName());
        user.setFullName(fullName);
        user.setMail(signInRequest.getEmail());
        var userCredentials = new UserCredentials();
        userCredentials.setUser(user);
        userCredentials.setUsername(signInRequest.getUsername());
        userCredentials.setPassword(encoder.encode(signInRequest.getPassword()));
        user.setUserCredentials(userCredentials);

        return user;
    }
}

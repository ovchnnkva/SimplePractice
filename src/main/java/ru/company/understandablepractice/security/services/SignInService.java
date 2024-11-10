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
        var password = signInRequest.getPassword();
        var name = String.format("%s %s %s", signInRequest.getLastName(), signInRequest.getFirstName(), signInRequest.getSecondName());

        if(userCredentialsService.isUserCredentialsAlreadyExists(username)){
            throw new UserAlreadyExists("User with such username already exists ");
        }
        var user = createUser(username, password, name);

        userService.create(user);
    }

    private User createUser(String username, String password, String name){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        var user = new User();
        user.setFullName(name);
        var userCredentials = new UserCredentials();
        userCredentials.setUser(user);
        userCredentials.setUsername(username);
        userCredentials.setPassword(encoder.encode(password));
        user.setUserCredentials(userCredentials);

        return user;
    }
}

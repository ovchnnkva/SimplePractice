package ru.company.understandablepractice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.repository.UserRepository;

@Service
public class UserService extends CRUDService<User>{

    UserService(UserRepository repository) {
        super(repository);
    }

    public Optional<User> findUserByLogin(String login){
        return this.findUserByLogin(login);
    }
}

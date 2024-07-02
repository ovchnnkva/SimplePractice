package ru.company.understandablepractice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.repository.UserRepository;

import java.math.BigInteger;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;

    public Optional<User> getById(long id) {
        return repository.findById(id);
    }

    public Optional<User> create(User user) {
        return Optional.of(repository.save(user));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}

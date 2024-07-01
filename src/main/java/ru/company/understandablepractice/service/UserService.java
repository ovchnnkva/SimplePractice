package ru.company.understandablepractice.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.repository.UserRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;

    public Optional<User> getById(long id) {
        return repository.findById(id);
    }

    public long create(User user) {
        return repository.save(user).getId();
    }

    public void delete(User user) {
        repository.delete(user);
    }
}

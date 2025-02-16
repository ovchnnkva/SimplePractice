package ru.company.understandablepractice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.UserResponse;
import ru.company.understandablepractice.dto.mapper.UserMapper;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.repository.UserRepository;

@Service
public class UserService extends CRUDService<User>{
    private final UserMapper mapper;

    UserService(UserRepository repository, UserMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    public Optional<User> findUserByLogin(String login){
        return this.findUserByLogin(login);
    }

    @Override
    public Optional<User> create(User entity) throws Exception {
        if(entity.getUserDiplomasList() != null && !entity.getUserDiplomasList().isEmpty()){
            entity.getUserDiplomasList().forEach(val -> val.setUserId(entity.getId()));
        }

        return super.create(entity);
    }

    public UserResponse update(UserResponse response) {
        Optional<User> entity = repository.findById(response.getId());
        entity.ifPresent(user -> {
            if(user.getUserDiplomasList() != null && !user.getUserDiplomasList().isEmpty()){
                user.getUserDiplomasList().forEach(val -> val.setUserId(user.getId()));
            }
            mapper.updateEntityFromDto(response, user);
            user.setFullName(String.format("%s %s %s", user.getLastName(), user.getFirstName(), user.getSecondName()));
            repository.save(user);
        });

        return response;
    }
}

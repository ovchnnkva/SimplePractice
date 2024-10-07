package ru.company.understandablepractice.security.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.PersonCredentials;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.model.UserCredentials;
import ru.company.understandablepractice.repository.UserCredentialsRepository;
import ru.company.understandablepractice.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCredentialsService {

    private final UserRepository userRepository;

    private final UserCredentialsRepository userCredentialsRepository;

    private final PersonCredentialsService personCredentialsService;

    public Optional<User> findUserByUserCredentialsId(Long id){
        return userRepository.findByUserCredentials_id(id);
    }

    public Optional<UserCredentials> findByUserId(Long userId){
        return userCredentialsRepository.findByUser_id(userId);
    }

    public UserCredentials findUserCredentialsByUsername(String username){
        Optional<UserCredentials> userCredentials = userCredentialsRepository.findByUsername(username);
        if(userCredentials.isPresent()){
            return userCredentials.get();
        }
        UserCredentials proxy = new UserCredentials();
        Optional<PersonCredentials> personCredentials = Optional.ofNullable(personCredentialsService.findUserCredentialsByUsername(username));
        if(personCredentials.isPresent()){
            proxy.setUsername(personCredentials.get().getUsername());
            proxy.setPassword(personCredentials.get().getPassword());
            proxy.setRoles(personCredentials.get().getRoles());
        }

        return proxy;
    }

    public boolean isUserCredentialsAlreadyExists(String username){
        return userCredentialsRepository.findByUsername(username).isPresent();
    }

    public UserDetailsService userDetailsService(){
        return this::findUserCredentialsByUsername;
    }
    
    public Optional<UserCredentials> findById(Long id){
        return userCredentialsRepository.findById(id);
    }
}

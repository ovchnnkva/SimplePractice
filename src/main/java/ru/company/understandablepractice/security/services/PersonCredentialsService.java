package ru.company.understandablepractice.security.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.model.PersonCredentials;
import ru.company.understandablepractice.repository.PersonCredentialsRepository;
import ru.company.understandablepractice.repository.PersonRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonCredentialsService {
    private final PersonRepository personRepository;

    private final PersonCredentialsRepository personCredentialsRepository;

    public Optional<Person> findUserByUserCredentialsId(Long id){
        return personRepository.findByPersonCredentials_id(id);
    }

    public Optional<PersonCredentials> findByPersonId(Long userId){
        return personCredentialsRepository.findByPerson_id(userId);
    }

    public PersonCredentials findUserCredentialsByUsername(String username){
        Optional<PersonCredentials> personCredentials = personCredentialsRepository.findByUsername(username);
        return personCredentials.get();
    }

    public boolean isUserCredentialsAlreadyExists(String username){
        return personCredentialsRepository.findByUsername(username).isPresent();
    }

    public UserDetailsService userDetailsService(){
        return this::findUserCredentialsByUsername;
    }

    public Optional<PersonCredentials> findById(Long id){
        return personCredentialsRepository.findById(id);
    }
}

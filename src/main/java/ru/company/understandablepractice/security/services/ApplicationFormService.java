package ru.company.understandablepractice.security.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.repository.PersonRepository;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.service.ChildService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static ru.company.understandablepractice.model.types.ApplicationFormStatus.CREATED;
import static ru.company.understandablepractice.model.types.ApplicationFormStatus.NOT_CREATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationFormService {
    private final PersonRepository personRepository;
    private final JwtService jwtService;
    private final HttpServletRequest request;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Optional<String> createLink(long id) {
        Person person;
        String link = null;
        try {
            person = personRepository.findPersonById(id).orElseThrow();
        } catch (NoSuchElementException exception) {
            throw new NoSuchElementException();
        }

        if(person.getApplicationFormStatus().equals(NOT_CREATED)) {
            setCredentials(person);
            link = String.format(
                    "%s/%s",
                    person.getClientType().toString(),
                    jwtService.generateToken(person.getPersonCredentials(), JwtType.PERSON)
            );
            log.info("create person link {}", link);
            updatePerson(person, link);
        }

        return Optional.ofNullable(link);
    }

    public Optional<String> getLink(long id) {
        Person person;
        String token = null;
        try {
            person = personRepository.findPersonById(id).orElseThrow();
        } catch (NoSuchElementException exception) {
            throw new NoSuchElementException();
        }

        if(person.getApplicationFormStatus().equals(CREATED)) {
            token = person.getApplicationFormToken();
            log.info("get person link {}", token);
        }

        return Optional.ofNullable(token);
    }

    public long getPersonId(String token) {
        return jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
    }

    private void setCredentials(Person person) {
        person.getPersonCredentials().setUsername(person.getMail());
        person.getPersonCredentials().setPassword(encoder.encode(String.valueOf(person.hashCode())));
    }

    private void updatePerson(Person person, String token) {
        person.setApplicationFormToken(token);
        person.setApplicationFormStatus(CREATED);
        personRepository.save(person);
    }
}

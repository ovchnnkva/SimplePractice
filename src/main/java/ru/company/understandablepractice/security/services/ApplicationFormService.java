package ru.company.understandablepractice.security.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Child;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.model.types.ApplicationFormStatus;
import ru.company.understandablepractice.repository.PersonRepository;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.service.ChildService;
import ru.company.understandablepractice.service.CustomerService;
import ru.company.understandablepractice.service.PairService;

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

    private final ChildService childService;
    private final PairService pairService;
    private final CustomerService customerService;

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
                    jwtService.generatePersonToken(person.getPersonCredentials())
            );
            log.info("create person link {}", link);
            updateApplicationFormData(person, link);
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

    public long getPersonId() {
        return jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
    }

    private void setCredentials(Person person) {
        person.getPersonCredentials().setUsername(person.getMail());
        person.getPersonCredentials().setPassword(encoder.encode(String.valueOf(person.hashCode())));
    }

    private void updateApplicationFormData(Person person, String token) {
        person.setApplicationFormToken(token);
        person.setApplicationFormStatus(CREATED);
        personRepository.save(person);
    }

    public Optional<Child> updateChild(Child child, long id) {
        child.setApplicationFormStatus(ApplicationFormStatus.PROCESSED);
        child.setApplicationFormToken("");
        child.setId(id);

        Optional<Child> newChild;

        try{
            newChild = childService.create(child);
        } catch (Exception e) {
            newChild = Optional.empty();
        }
        return newChild;
    }

    public Optional<Pair> updatePair(Pair pair, long id) {
        pair.setApplicationFormStatus(ApplicationFormStatus.PROCESSED);
        pair.setApplicationFormToken("");
        pair.setId(id);

        Optional<Pair> newPair;

        try{
            newPair = pairService.create(pair);
        } catch (Exception e) {
            newPair = Optional.empty();
        }
        return newPair;
    }

    public Optional<Customer> updateCustomer(Customer customer, long id) {
        customer.setApplicationFormStatus(ApplicationFormStatus.PROCESSED);
        customer.setApplicationFormToken("");
        customer.setId(id);

        Optional<Customer> newCustomer;

        try{
            newCustomer = customerService.create(customer);
        } catch (Exception e) {
            newCustomer = Optional.empty();
        }
        return newCustomer;
    }

}

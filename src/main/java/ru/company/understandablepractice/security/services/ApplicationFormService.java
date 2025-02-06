package ru.company.understandablepractice.security.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.customers.ChildResponse;
import ru.company.understandablepractice.dto.customers.CustomerResponse;
import ru.company.understandablepractice.dto.customers.PairResponse;
import ru.company.understandablepractice.dto.mapper.ChildMapper;
import ru.company.understandablepractice.dto.mapper.CustomerMapper;
import ru.company.understandablepractice.dto.mapper.PairMapper;
import ru.company.understandablepractice.model.*;
import ru.company.understandablepractice.model.types.ApplicationFormStatus;
import ru.company.understandablepractice.repository.ChildRepository;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.PairRepository;
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
    private final JwtService jwtService;
    private final HttpServletRequest request;

    private final ChildRepository childRepository;
    private final PairRepository pairRepository;
    private final CustomerRepository customerRepository;

    private final PairMapper pairMapper;
    private final CustomerMapper customerMapper;
    private final ChildMapper childMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Optional<String> createLink(long id) {
        Customer customer;
        String link = null;
        try {
            customer = customerRepository.findCustomerById(id).orElseThrow();
        } catch (NoSuchElementException exception) {
            throw new NoSuchElementException();
        }

        if(customer.getApplicationFormStatus().equals(NOT_CREATED)) {
            setCredentials(customer);
            String token = jwtService.generatePersonToken(customer.getCustomerCredentials());
            link = String.format(
                    "%s/%s",
                    customer.getClientType().toString(),
                    token
            );
            log.info("create person link {}", link);
            updateApplicationFormData(customer, token);
        }

        return Optional.ofNullable(link);
    }

    public Optional<String> getLink(long id) {
        Customer customer;
        String token = null;
        try {
            customer = customerRepository.findCustomerById(id).orElseThrow();
            updateStatusIfInvalidToken(customer);
        } catch (NoSuchElementException exception) {
            throw new NoSuchElementException();
        }

        if(customer.getApplicationFormStatus().equals(CREATED)) {
            token = customer.getClientType() + "/" + customer.getApplicationFormToken();
            log.info("get person link {}", token);
        }


        return Optional.ofNullable(token);
    }

    private void updateStatusIfInvalidToken(Customer customer) {
        if(jwtService.isTokenExpired(customer.getApplicationFormToken(), JwtType.ACCESS)) {
            customer.setApplicationFormStatus(ApplicationFormStatus.INVALID);
            customerRepository.save(customer);
        }
    }

    public long getPersonId() {
        return jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);
    }

    private void setCredentials(Customer customer) {
        customer.getCustomerCredentials().setUsername(customer.getMail());
        customer.getCustomerCredentials().setPassword(encoder.encode(String.valueOf(customer.hashCode())));
    }

    private void updateApplicationFormData(Customer customer, String token) {
        customer.setApplicationFormToken(token);
        customer.setApplicationFormStatus(CREATED);
        customerRepository.save(customer);
    }

    public ChildResponse update(ChildResponse response, long id) {
        Child child = childMapper.fromResponseToEntity(response);
        child.setApplicationFormStatus(ApplicationFormStatus.PROCESSED);
        child.setApplicationFormToken("");
        child.setId(id);

        //todo отрефакторить этот ужас
        Child oldChild = childRepository.findById(id).orElseThrow();
        child.setUser(oldChild.getUser());

        childRepository.save(child);

        return response;
    }

    public PairResponse update(PairResponse response, long id) {
        Pair pair = pairMapper.fromResponseToEntity(response);
        pair.setApplicationFormStatus(ApplicationFormStatus.PROCESSED);
        pair.setApplicationFormToken("");
        pair.setId(id);

        //todo отрефакторить этот ужас
        Pair oldPair = pairRepository.findById(id).orElseThrow();
        pair.setUser(oldPair.getUser());

        pairRepository.save(pair);

        return response;
    }

    public CustomerResponse update(CustomerResponse response, long id) {
        Customer customer = customerMapper.fromResponseToEntity(response);
        customer.setApplicationFormStatus(ApplicationFormStatus.PROCESSED);
        customer.setApplicationFormToken("");
        customer.setId(id);

        //todo отрефакторить этот ужас
        Customer oldCustomer = customerRepository.findById(id).orElseThrow();
        customer.setUser(oldCustomer.getUser());

        customerRepository.save(customer);

        return response;
    }

}

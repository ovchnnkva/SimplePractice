package ru.company.understandablepractice.security.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.CustomerCredentials;
import ru.company.understandablepractice.repository.CustomerCredentialsRepository;
import ru.company.understandablepractice.repository.CustomerRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerCredentialsService {
    private final CustomerRepository customerRepository;

    private final CustomerCredentialsRepository customerCredentialsRepository;

    public Optional<Customer> findUserByUserCredentialsId(Long id) {
        return customerRepository.findByCustomerCredentials_id(id);
    }

    public Optional<CustomerCredentials> findByCustomerId(Long userId) {
        return customerCredentialsRepository.findByCustomer_id(userId);
    }

    public CustomerCredentials findUserCredentialsByUsername(String username) {
        Optional<CustomerCredentials> customerCredentials = customerCredentialsRepository.findByUsername(username);
        return customerCredentials.get();
    }

    public Optional<CustomerCredentials> findCustomerCredentialsByToken(String token) {
        Optional<Customer> customer = customerRepository.findByApplicationFormToken(token);
        return customer.map(Customer::getCustomerCredentials);
    }

    public boolean isUserCredentialsAlreadyExists(String username) {
        return customerCredentialsRepository.findByUsername(username).isPresent();
    }

    public UserDetailsService userDetailsService() {
        return this::findUserCredentialsByUsername;
    }

    public Optional<CustomerCredentials> findById(Long id) {
        return customerCredentialsRepository.findById(id);
    }
}

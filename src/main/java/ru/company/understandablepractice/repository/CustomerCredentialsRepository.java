package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.understandablepractice.model.CustomerCredentials;

import java.util.Optional;

public interface CustomerCredentialsRepository extends JpaRepository<CustomerCredentials, Long> {
    Optional<CustomerCredentials> findByUsername(String username);
    public Optional<CustomerCredentials> findByCustomer_id(Long id);
}

package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.understandablepractice.model.PersonCredentials;

import java.util.Optional;

public interface PersonCredentialsRepository extends JpaRepository<PersonCredentials, Long> {
    Optional<PersonCredentials> findByUsername(String username);
    public Optional<PersonCredentials> findByPerson_id(Long id);
}

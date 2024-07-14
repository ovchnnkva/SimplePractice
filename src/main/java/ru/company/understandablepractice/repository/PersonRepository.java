package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}

package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value =
            "SELECT p " +
            "FROM Person p " +
            "WHERE p.fullName LIKE %:name%")
    Optional<List<Person>> findPersonsByName (@Param("name") String name);
}

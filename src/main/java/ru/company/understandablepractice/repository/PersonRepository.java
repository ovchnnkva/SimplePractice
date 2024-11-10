package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.model.types.ClientStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value =
            "SELECT p " +
                    "FROM Person p " +
                    "WHERE LOWER(p.fullName) LIKE LOWER(CONCAT('%', :name,'%')) AND p.user.id = :userId " +
                    "ORDER BY p.id DESC")
    Optional<List<Person>> findPersonsByName(@Param("userId") long userId, @Param("name") String name);

    @Query(value =
            "SELECT p " +
                    "FROM Person p " +
                    "WHERE LOWER(p.fullName) LIKE LOWER(CONCAT('%', :name,'%')) AND p.user.id = :userId " +
                    "ORDER BY p.id DESC " +
                    "OFFSET :offset ROWS " +
                    "FETCH NEXT :limit ROWS ONLY")
    Optional<List<Person>> findPersonsByNamePagination(@Param("userId") long userId, @Param("name") String name,
                                                       @Param("offset") long offset, @Param("limit") long limit);


    @Query(value =
            "SELECT p " +
                    "FROM Person p " +
                    "WHERE p.user.id = :userId " +
                    "ORDER BY p.id DESC " +
                    "OFFSET :offset ROWS " +
                    "FETCH NEXT :limit ROWS ONLY")
    Optional<List<Person>> findAllPagination(@Param("userId") long userId, @Param("offset") long offset,
                                             @Param("limit") long limit);

    @Query(value =
            "SELECT p " +
                    "FROM Person p " +
                    "WHERE p.user.id = :userId AND p.clientStatus = :status")
    Optional<List<Person>> findNewPersonByUserAndStatus(long userId, ClientStatus status);

    Optional<Person> findPersonById(long id);

    public Optional<Person> findByPersonCredentials_id(Long id);

    public Optional<Person> findByApplicationFormToken(String token);
}

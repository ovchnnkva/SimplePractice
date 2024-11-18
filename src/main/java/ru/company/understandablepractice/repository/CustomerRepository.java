package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.types.ClientStatus;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value =
            "SELECT c " +
            "FROM Customer c " +
            "WHERE c.user.id = :userId AND c.clientStatus = :status"
    )
    Optional<List<Customer>> findNewCustomerByUserAndStatus(long userId, ClientStatus status);

    @Query(value =
            "SELECT c " +
                    "FROM Customer c " +
                    "WHERE LOWER(c.fullName) LIKE LOWER(CONCAT('%', :name,'%')) AND c.user.id = :userId " +
                    "ORDER BY c.id DESC " +
                    "OFFSET :offset ROWS " +
                    "FETCH NEXT :limit ROWS ONLY")
    Optional<List<Customer>> findCustomersByNamePagination (@Param("userId") long userId, @Param("name") String name,
                                                        @Param("offset") long offset, @Param("limit") long limit);

    @Query(value =
            "SELECT c " +
                    "FROM Customer c " +
                    "WHERE c.user.id = :userId " +
                    "ORDER BY c.id DESC " +
                    "OFFSET :offset ROWS " +
                    "FETCH NEXT :limit ROWS ONLY")
    Optional<List<Customer>> findAllPagination (@Param("userId") long userId, @Param("offset") long offset,
                                              @Param("limit") long limit);

    @Query(value =
            "SELECT c " +
                    "FROM Customer c " +
                    "WHERE LOWER(c.fullName) LIKE LOWER(CONCAT('%', :name,'%')) AND c.user.id = :userId " +
                    "ORDER BY c.id DESC")
    Optional<List<Customer>> findCustomersByName (@Param("userId") long userId, @Param("name") String name);

    Optional<Customer> findCustomerById(long id);

    public Optional<Customer> findByCustomerCredentials_id(Long id);

    public Optional<Customer> findByApplicationFormToken(String token);
}

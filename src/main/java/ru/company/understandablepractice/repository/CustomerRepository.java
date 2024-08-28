package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}

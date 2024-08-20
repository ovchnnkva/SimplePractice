package ru.company.understandablepractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.company.understandablepractice.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {


}

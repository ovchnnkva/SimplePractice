package ru.company.understandablepractice.service;

import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.repository.CustomerRepository;

@Service
public class CustomerService extends CRUDService<Customer>{

    CustomerService(CustomerRepository repository) {
        super(repository);
    }

}

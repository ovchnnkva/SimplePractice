package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Child;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.repository.ChildRepository;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.PairRepository;

@Slf4j
@Service
public class CustomerService extends CRUDService<Customer>{

    private final CustomerRepository customerRepository;
    private final ChildRepository childRepository;
    private final PairRepository pairRepository;

    CustomerService(CustomerRepository customerRepository, ChildRepository childRepository, PairRepository pairRepository) {
        super(customerRepository);
        this.customerRepository = customerRepository;
        this.childRepository = childRepository;
        this.pairRepository = pairRepository;
    }

    public long saveCustomer(Customer customer) throws Exception {
        long id;
        log.info("save {} {}", customer.getClientType().getTittle(), customer);
        switch (customer.getClientType()) {
            case CUSTOMER -> id = customerRepository.save(new Customer()).getId();
            case CHILD -> id = childRepository.save(new Child(customer)).getId();
            case PAIR -> id = pairRepository.save(new Pair(customer)).getId();
            default -> throw new Exception("Неверный тип клиента");
        }

        return id;
    }
}

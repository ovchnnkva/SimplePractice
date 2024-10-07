package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Child;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.repository.ChildRepository;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.PairRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final CustomerRepository customerRepository;
    private final ChildRepository childRepository;
    private final PairRepository pairRepository;

    public long  savePerson(Person person) throws Exception {
        long id;
        log.info("save {} {}", person.getClientType().getTittle(), person);
        switch (person.getClientType()) {
            case CUSTOMER -> id = customerRepository.save(new Customer(person)).getId();
            case CHILD -> id = childRepository.save(new Child(person)).getId();
            case PAIR -> id = pairRepository.save(new Pair(person)).getId();
            default -> throw new Exception("Неверный тип клиента");
        }

        return id;
    }
}

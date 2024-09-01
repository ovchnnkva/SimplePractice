package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Child;
import ru.company.understandablepractice.repository.ChildRepository;
import ru.company.understandablepractice.repository.CustomerRepository;

import java.util.Optional;

@Slf4j
@Service
public class ChildService extends CRUDService<Child>{
    private final CustomerRepository customerRepository;

    ChildService(ChildRepository repository, CustomerRepository customerRepository) {
        super(repository);
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Child> create(Child entity) throws Exception {
        if (entity.getFirstParent().getId() == 0) {
            log.info("save first parent");
            entity.getFirstParent().setId(customerRepository.save(entity.getFirstParent()).getId());
        }

        if (entity.getSecondParent().getId() == 0) {
            log.info("save second parent");
            entity.getSecondParent().setId(customerRepository.save(entity.getSecondParent()).getId());
        }

        return super.create(entity);
    }
}

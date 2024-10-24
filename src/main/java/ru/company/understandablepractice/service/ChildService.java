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
        if (entity.getFirstParent() != null && entity.getFirstParent().getId() == 0) {
            log.info("save first parent");
            entity.getFirstParent().setUser(entity.getUser());
            entity.setFirstParent(customerRepository.save(entity.getFirstParent()));
        }

        if (entity.getSecondParent() != null && entity.getSecondParent().getId() == 0) {
            log.info("save second parent");
            entity.getSecondParent().setUser(entity.getUser());
            entity.setSecondParent(customerRepository.save(entity.getSecondParent()));
        }

        return super.create(entity);
    }
}

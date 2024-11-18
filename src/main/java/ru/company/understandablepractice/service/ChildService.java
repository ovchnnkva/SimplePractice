package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Child;
import ru.company.understandablepractice.repository.ChildRepository;
import ru.company.understandablepractice.repository.PersonRepository;

import java.util.Optional;

@Slf4j
@Service
public class ChildService extends CRUDService<Child>{
    private final PersonRepository personRepository;

    ChildService(ChildRepository repository, PersonRepository personRepository) {
        super(repository);
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Child> create(Child entity) throws Exception {
        if (entity.getFirstParent().getId() == 0) {
            log.info("save first parent");
            entity.getFirstParent().setId(personRepository.save(entity.getFirstParent()).getId());
        }

        if (entity.getSecondParent().getId() == 0) {
            log.info("save second parent");
            entity.getSecondParent().setId(personRepository.save(entity.getSecondParent()).getId());
        }

        return super.create(entity);
    }
}

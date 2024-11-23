package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.repository.PairRepository;
import ru.company.understandablepractice.repository.PersonRepository;

import java.util.Optional;


@Slf4j
@Service
public class PairService extends CRUDService<Pair> {
    private final PersonRepository personRepository;

    PairService(PairRepository repository, PersonRepository personRepository) {
        super(repository);
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Pair> create(Pair entity) throws Exception {

        if(entity.getSecondPerson() != null && entity.getSecondPerson().getId() == 0) {
            log.info("create person for pair");
            entity.getSecondPerson().setUser(entity.getUser());
            entity.getSecondPerson().setId(personRepository.save(entity.getSecondPerson()).getId());
        }

        return super.create(entity);
    }
}

package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.MeetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MeetService extends CRUDService<Meet>{
    private final MeetRepository repository;
    private final PersonService personService;

    MeetService(MeetRepository repository, PersonService personService) {
        super(repository);
        this.repository = repository;
        this.personService = personService;
    }

    @Override
    public Optional<Meet> create(Meet entity) throws Exception {
        if(entity.getPerson() != null && entity.getPerson().getId() == 0) {
            entity.getPerson().setId(personService.savePerson(entity.getPerson()));
        }
        return super.create(entity);
    }

    public List<Meet> getByUserIdAndPersonId(long userId, long personId, long offset, long limit) {
        return repository.findByUserIdAndPersonId(userId, personId, offset, limit);
    }
}

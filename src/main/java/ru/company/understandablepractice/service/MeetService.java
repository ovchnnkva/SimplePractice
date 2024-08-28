package ru.company.understandablepractice.service;

import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.repository.MeetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeetService extends CRUDService<Meet>{
    private final MeetRepository repository;
    MeetService(MeetRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Meet> getByUserIdAndPersonId(long userId, long personId) {
        return repository.findByUserIdAndPersonId(userId, personId);
    }
}

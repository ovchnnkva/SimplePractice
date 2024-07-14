package ru.company.understandablepractice.service;

import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.repository.MeetRepository;

@Service
public class MeetService extends CRUDService<Meet>{
    MeetService(MeetRepository repository) {
        super(repository);
    }
}

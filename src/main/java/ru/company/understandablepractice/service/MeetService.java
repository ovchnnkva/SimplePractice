package ru.company.understandablepractice.service;

import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.repository.MeetRepository;

public class MeetService extends CRUDService<Meet>{
    MeetService(MeetRepository repository) {
        super(repository);
    }
}

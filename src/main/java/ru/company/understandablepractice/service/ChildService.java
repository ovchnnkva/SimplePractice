package ru.company.understandablepractice.service;

import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Child;
import ru.company.understandablepractice.repository.ChildRepository;

@Service
public class ChildService extends CRUDService<Child>{
    ChildService(ChildRepository repository) {
        super(repository);
    }
}

package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.TypeMethod;
import ru.company.understandablepractice.repository.TypeMethodRepository;

@Slf4j
@Service
public class TypeMethodService extends CRUDService<TypeMethod> {
    TypeMethodService(TypeMethodRepository repository) {
        super(repository);
    }
}

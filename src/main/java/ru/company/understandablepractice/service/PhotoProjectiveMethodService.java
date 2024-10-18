package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.PhotoProjectiveMethod;
import ru.company.understandablepractice.repository.PhotoProjectiveMethodRepository;

@Slf4j
@Service
public class PhotoProjectiveMethodService extends CRUDService<PhotoProjectiveMethod> {
    PhotoProjectiveMethodService(PhotoProjectiveMethodRepository repository) {
        super(repository);
    }
}

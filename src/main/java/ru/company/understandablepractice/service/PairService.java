package ru.company.understandablepractice.service;

import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.repository.PairRepository;

@Service
public class PairService extends CRUDService<Pair> {
    PairService(PairRepository repository) {
        super(repository);
    }
}

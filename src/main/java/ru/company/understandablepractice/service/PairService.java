package ru.company.understandablepractice.service;

import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.repository.PairRepository;

public class PairService extends CRUDService<Pair> {
    PairService(PairRepository repository) {
        super(repository);
    }
}

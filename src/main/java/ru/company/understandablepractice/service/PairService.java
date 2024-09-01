package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.CustomerResponse;
import ru.company.understandablepractice.model.Pair;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.PairRepository;

import java.util.Optional;


@Slf4j
@Service
public class PairService extends CRUDService<Pair> {
    private final CustomerRepository customerRepository;

    PairService(PairRepository repository, CustomerRepository customerRepository) {
        super(repository);
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Pair> create(Pair entity) throws Exception {

        if(entity.getSecondCustomer().getId() == 0) {
            log.info("create customer for pair");
            entity.getSecondCustomer().setId(customerRepository.save(entity.getSecondCustomer()).getId());
        }

        return super.create(entity);
    }
}

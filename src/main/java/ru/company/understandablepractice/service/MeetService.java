package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.repository.MeetRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MeetService extends CRUDService<Meet>{
    private final MeetRepository repository;
    private final CustomerService customerService;

    MeetService(MeetRepository repository, CustomerService customerService) {
        super(repository);
        this.repository = repository;
        this.customerService = customerService;
    }

    @Override
    public Optional<Meet> create(Meet entity) throws Exception {
        if(entity.getCustomer() != null && entity.getCustomer().getId() == 0) {
            entity.getCustomer().setId(customerService.saveCustomer(entity.getCustomer()));
        }
        return super.create(entity);
    }

    public List<Meet> getByUserIdAndCustomerId(long userId, long customerId, long offset, long limit) {
        return repository.findByUserIdAndCustomerId(userId, customerId, offset, limit);
    }
}

package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.CustomerMeetInfoResponse;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.mapper.CustomerMeetInfoMapper;
import ru.company.understandablepractice.dto.mapper.MeetMapper;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.repository.MeetRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MeetService extends CRUDService<Meet>{
    private final MeetRepository repository;
    private final CustomerMeetInfoMapper customerMeetInfoMapper;
    private final CustomerService customerService;
    private final MeetMapper mapper;

    MeetService(MeetRepository repository, CustomerMeetInfoMapper customerMeetInfoMapper,
                CustomerService customerService, MeetMapper mapper) {
        super(repository);
        this.repository = repository;
        this.customerMeetInfoMapper = customerMeetInfoMapper;
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @Override
    public Optional<Meet> create(Meet entity) throws Exception {
        if(entity.getCustomer() != null && entity.getCustomer().getId() == 0) {
            entity.getCustomer().setId(customerService.saveCustomer(entity.getCustomer()));
        }
        return super.create(entity);
    }

    public MeetResponse update(MeetResponse response) {
        Optional<Meet> entity = repository.findById(response.getId());
        entity.ifPresent(meet -> mapper.updateEntityFromDto(response, meet));

        return response;
    }

    public List<Meet> getByUserIdAndCustomerId(long userId, long customerId, long offset, long limit) {
        return repository.findByUserIdAndCustomerId(userId, customerId, offset, limit);
    }

    public Optional<CustomerMeetInfoResponse> getCustomerMeetInfo(long customerId) {
        CustomerMeetInfoResponse response = null;
        List<Meet> meets = repository.findMeetByCustomerId(customerId).orElse(null);
        if (meets != null) {
            response = customerMeetInfoMapper.fromEntityToResponse(customerId, meets);
        }

        return Optional.ofNullable(response);
    }
}

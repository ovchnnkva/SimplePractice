package ru.company.understandablepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.CustomerMeetInfoResponse;
import ru.company.understandablepractice.dto.MeetResponse;
import ru.company.understandablepractice.dto.mapper.CustomerMeetInfoMapper;
import ru.company.understandablepractice.dto.mapper.MeetMapper;
import ru.company.understandablepractice.model.Meet;
import ru.company.understandablepractice.repository.MeetRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MeetService extends CRUDService<Meet> {
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
        if (entity.getCustomer() != null && entity.getCustomer().getId() == 0) {
            entity.getCustomer().setId(customerService.saveCustomer(entity.getCustomer()));
        }

        if (entity.getNameMeet() == null || entity.getNameMeet().isEmpty()) {
            entity.setNameMeet("Встреча");
        }

        return super.create(entity);
    }

    public MeetResponse update(MeetResponse response) {
        Optional<Meet> entity = repository.findById(response.getId());
        entity.ifPresent(meet -> {
            mapper.updateEntityFromDto(response, meet);
            repository.save(meet);
        });

        return response;
    }

    public List<Meet> getByUserIdAndCustomerId(long userId, long customerId, long offset, long limit) {
        return repository.findByUserIdAndCustomerId(userId, customerId, offset, limit);
    }

    public Optional<CustomerMeetInfoResponse> getCustomerMeetInfo(long customerId) {
        CustomerMeetInfoResponse response = new CustomerMeetInfoResponse();
        List<Meet> meets = repository.findMeetByCustomerId(customerId);
        if (meets != null) {
            Optional<Meet> last = meets.stream()
                    .filter(meet -> meet.getDateMeet().isBefore(LocalDate.now()))
                    .max(Comparator.comparing(Meet::getDateMeet));
            Optional<Meet> next = meets.stream()
                    .filter(meet -> meet.getDateMeet().isAfter(LocalDate.now()))
                    .min(Comparator.comparing(Meet::getDateMeet));

            last.ifPresent(meet -> {
                response.setLastMeetDate(meet.getDateMeet());
                response.setLastMeetStart(meet.getStartMeet());
                response.setLastMeetEnd(meet.getEndMeet());
            });
            next.ifPresent(meet -> {
                response.setNextMeetDate(meet.getDateMeet());
                response.setNextMeetStart(meet.getStartMeet());
                response.setNextMeetEnd(meet.getEndMeet());
            });
            response.setCountMeet(meets.size());
        }

        return Optional.of(response);
    }
}

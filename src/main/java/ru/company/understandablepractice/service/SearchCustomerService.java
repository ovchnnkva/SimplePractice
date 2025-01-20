package ru.company.understandablepractice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.SearchCustomerResponse;
import ru.company.understandablepractice.dto.mapper.SearchCustomerMapper;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.model.types.ClientStatus;
import ru.company.understandablepractice.model.types.converters.ClientStatusConverter;
import ru.company.understandablepractice.model.types.converters.ClientTypeConverter;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.MeetRepository;
import ru.company.understandablepractice.security.JwtType;
import ru.company.understandablepractice.security.services.JwtService;
import ru.company.understandablepractice.specification.CustomerSpecification;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchCustomerService {
    private final CustomerRepository customerRepository;
    private final SearchCustomerMapper searchCustomerMapper;
    private final HttpServletRequest request;
    private final JwtService jwtService;

    private final ClientTypeConverter clientTypeConverter = new ClientTypeConverter();
    private final ClientStatusConverter clientStatusConverter = new ClientStatusConverter();


    @Transactional
    public List<SearchCustomerResponse> findByName(String customerName, Pageable pageable,
                                                   String orderDate, String orderMeetCount,
                                                   String clientStatus, String clientType){
        Long userId = jwtService.extractUserId(request.getHeader("Authorization"), JwtType.ACCESS);

        List<Customer> customerList = customerRepository.findAll(buildSpec(customerName, userId, orderDate, orderMeetCount, clientStatus, clientType), pageable).getContent();
        customerList.forEach(customer -> Hibernate.initialize(customer.getMeets()));
        return customerList.stream()
                .map(searchCustomerMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    private Specification<Customer> buildSpec(String customerName, long userId,
                                              String orderDate, String orderMeetCount,
                                              String clientStatus, String clientType) {
        return CustomerSpecification.hasUser(userId)
                .and(CustomerSpecification.containingFullName(customerName))
                .and(CustomerSpecification.sortByDateMeet(orderDate))
                .and(CustomerSpecification.sortByMeetsSize(orderMeetCount))
                .and(CustomerSpecification.hasClientStatus(clientStatusConverter.convertToEntityAttribute(clientStatus)))
                .and(CustomerSpecification.hasClientType(clientTypeConverter.convertToEntityAttribute(clientType)));
    }
}

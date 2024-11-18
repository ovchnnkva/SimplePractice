package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.SearchCustomerResponse;
import ru.company.understandablepractice.dto.mapper.SearchCustomerMapper;
import ru.company.understandablepractice.model.Customer;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.MeetRepository;
import ru.company.understandablepractice.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchCustomerService {
    private final PersonRepository personRepository;
    private final CustomerRepository customerRepository;
    private final MeetRepository meetRepository;
    private final SearchCustomerMapper searchCustomerMapper;

    public Optional<List<SearchCustomerResponse>> findByName(long userId, String name, long offset, long limit){
        List<SearchCustomerResponse> response = null;
        List<Customer> customerList = null;

        if (name != null && !name.isEmpty()) {
            customerList = customerRepository.findCustomersByNamePagination(userId, name, offset, limit).orElse(null);
        } else {
            customerList = customerRepository.findAllPagination(userId, offset, limit).orElse(null);
        }

        if (customerList != null){
            response = customerList.stream().map(customer -> {
                var meets = meetRepository.findClientDateMeet(customer.getId()).orElse(new ArrayList<>());
                return searchCustomerMapper.fromEntityToResponse(customer, meets);
            }).collect(Collectors.toList());
        }

        return Optional.ofNullable(response);
    }
}

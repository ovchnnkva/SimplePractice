package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.HeaderSearchPersonResponse;
import ru.company.understandablepractice.dto.mapper.HeaderSearchPersonMapper;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.repository.CustomerRepository;
import ru.company.understandablepractice.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeaderService {
    private final PersonRepository personRepository;
    private final CustomerRepository customerRepository;
    private final HeaderSearchPersonMapper searchPersonMapper;

    public Optional<List<HeaderSearchPersonResponse>> findPersonsByName(long userId, String name){
        List<HeaderSearchPersonResponse> response = null;

        List<Person> personList = customerRepository.findCustomersByName(userId, name).orElse(null);
        if(personList != null) {
            response = personList.stream().map(searchPersonMapper::fromEntityToResponse).collect(Collectors.toList());
        }

        return Optional.ofNullable(response);
    }
}

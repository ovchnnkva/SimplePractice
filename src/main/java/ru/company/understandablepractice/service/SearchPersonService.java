package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.SearchPersonResponse;
import ru.company.understandablepractice.dto.mapper.SearchPersonMapper;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.repository.MeetRepository;
import ru.company.understandablepractice.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchPersonService {
    private final PersonRepository personRepository;
    private final MeetRepository meetRepository;
    private final SearchPersonMapper searchPersonMapper;

    public Optional<List<SearchPersonResponse>> findByName(String name){
        List<SearchPersonResponse> response = null;

        List<Person> personList = personRepository.findPersonsByName(name).orElse(new ArrayList<>());

        response = personList.stream().map(person -> {
            var meets = meetRepository.findClientDateMeet(person.getId()).orElse(new ArrayList<>());
            return searchPersonMapper.fromEntityToResponse(person, meets);
        }).collect(Collectors.toList());

        return Optional.of(response);
    }
}

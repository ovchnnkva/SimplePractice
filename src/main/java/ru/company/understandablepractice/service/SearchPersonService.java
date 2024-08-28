package ru.company.understandablepractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.company.understandablepractice.dto.PersonResponse;
import ru.company.understandablepractice.dto.SearchPersonResponse;
import ru.company.understandablepractice.dto.mapper.PersonMapper;
import ru.company.understandablepractice.model.Person;
import ru.company.understandablepractice.repository.MeetRepository;
import ru.company.understandablepractice.repository.PersonRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchPersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final MeetRepository meetRepository;

    public List<SearchPersonResponse> findByName(String name){

        List<PersonResponse> persons = getPersonsByName(name);
        return persons.stream().map(person -> {
            var searchPerson = new SearchPersonResponse();
            searchPerson.setFullName(person.getFullName());
            searchPerson.setYears(Period.between(person.getBirth(), LocalDate.now()).getYears());
            searchPerson.setClientType(person.getClientType());
            searchPerson.setMail(person.getMail());
            searchPerson.setPhone(person.getPhoneNumber());

            List<LocalDate> meetInfo = getMeetInfo(person.getId());
            if (!meetInfo.isEmpty()) {
                searchPerson.setMeetDate(meetInfo.get(0));
                searchPerson.setCountMeet(meetInfo.size());
            }

            searchPerson.setClientStatus(person.getClientStatus());
            searchPerson.setMeetingType(person.getMeetingFormat());

            return searchPerson;
        }).collect(Collectors.toList());
    }

    private List<LocalDate> getMeetInfo (long personId) {
        return meetRepository.findClientDateMeet(personId).orElseGet(ArrayList::new);
    }

    private List<PersonResponse> getPersonsByName(String name) {
        List<Person> personsList = personRepository.findPersonsByName(name).orElse(null);
        if (personsList != null){
            return personsList.stream().map(personMapper::fromEntityToResponse).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}

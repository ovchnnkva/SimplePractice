package ru.company.understandablepractice.dto;

import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.types.ClientType;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
public class PersonResponse {

    private long id;

    private String fullName;

    private String firstName;

    private String secondName;

    private String lastName;

    private LocalDate birth;

    private String phoneNumber;

    private String mail;

    private String gender;

    public PersonResponse() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonResponse that = (PersonResponse) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

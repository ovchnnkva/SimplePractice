package ru.company.understandablepractice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.types.ClientType;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
public class PersonResponse {

    protected long id;

    protected String clientType;

    protected String fullName;

    protected String firstName;

    protected String secondName;

    protected String lastName;

    protected LocalDate birth;

    protected String phoneNumber;

    protected String mail;

    protected String gender;

    private String clientStatus;

    private String meetingFormat;

    public PersonResponse(String clientType) {
        this.clientType = clientType;
    }

    public PersonResponse() {
        this.clientType = ClientType.ADULT.getTittle();
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

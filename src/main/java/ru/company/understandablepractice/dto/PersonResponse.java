package ru.company.understandablepractice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.company.understandablepractice.model.types.ClientType;

import java.util.Objects;

@Getter
public class PersonResponse {
    @Setter
    protected long id;
    @JsonIgnore
    private final int clientType;
    @Setter
    protected String fullName;
    @Setter
    protected String firstName;
    @Setter
    protected String secondName;
    @Setter
    protected String lastName;
    @Setter
    protected String phoneNumber;
    @Setter
    protected String mail;
    @Setter
    protected String gender;

    public PersonResponse(int clientType) {
        this.clientType = clientType;
    }

    public PersonResponse() {
        this.clientType = ClientType.ADULT.getKey();
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

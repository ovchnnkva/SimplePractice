package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.company.understandablepractice.model.types.*;
import ru.company.understandablepractice.model.types.converters.*;

import java.time.LocalDate;
import java.util.Objects;

@ToString
@Getter
@Setter
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    protected long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    protected User user;

    @Convert(converter = ClientTypeConverter.class)
    @Column(name = "client_type")
    protected ClientType clientType;

    @Column(name = "full_name")
    protected String fullName;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "second_name")
    protected String secondName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "date_of_birth")
    protected LocalDate birth;

    @Column(name = "phone_number", length = 20)
    protected String phoneNumber;

    @Column(name = "mail")
    protected String mail;

    @Convert(converter = GenderConverter.class)
    @Column(name = "gender")
    protected Gender gender;

    @Column(name = "date_of_first_request")
    protected LocalDate dateFirstRequest;

    @Convert(converter = ClientStatusConverter.class)
    @Column(name = "client_status")
    protected ClientStatus clientStatus;

    @Convert(converter = MeetingFormatConverter.class)
    @Column(name = "meeting_format")
    protected MeetingFormat meetingFormat;

    @Column(name = "application_form_status")
    @Convert(converter = ApplicationFormStatusConverter.class)
    protected ApplicationFormStatus applicationFormStatus = ApplicationFormStatus.NOT_CREATED;

    @Column(name = "application_form_token", columnDefinition = "TEXT")
    protected String applicationFormToken;

    @OneToOne(
            mappedBy = "person",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private PersonCredentials personCredentials;

    public Person(long id) {
        this.id = id;
    }

    public Person() {
    }

    public void setPersonCredentials(PersonCredentials personCredentials) {
        personCredentials.setPerson(this);
        this.personCredentials = personCredentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

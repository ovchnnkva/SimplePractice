package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.types.ClientStatus;
import ru.company.understandablepractice.model.types.ClientType;
import ru.company.understandablepractice.model.types.Gender;
import ru.company.understandablepractice.model.types.MeetingFormat;
import ru.company.understandablepractice.model.types.converters.ClientStatusConverter;
import ru.company.understandablepractice.model.types.converters.ClientTypeConverter;
import ru.company.understandablepractice.model.types.converters.GenderConverter;
import ru.company.understandablepractice.model.types.converters.MeetingFormatConverter;

import java.time.LocalDate;

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
    private LocalDate birth;

    @Column(name = "phone_number", length = 20)
    protected String phoneNumber;

    @Column(name = "mail")
    protected String mail;

    @Convert(converter = GenderConverter.class)
    @Column(name = "gender")
    protected Gender gender;

    @Convert(converter = ClientStatusConverter.class)
    @Column(name = "client_status")
    private ClientStatus clientStatus;

    @Convert(converter = MeetingFormatConverter.class)
    @Column(name = "meeting_format")
    private MeetingFormat meetingFormat;

    public Person(long id) {
        this.id = id;
    }

    public Person() {
    }
}

package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.types.*;
import ru.company.understandablepractice.model.types.converters.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends Person{
    @Convert(converter = ContactMethodConverter.class)
    @Column(name = "contact_method")
    private ContactMethod contactMethod;

    @Column(name = "date_of_first_consultation")
    private LocalDate dateFirstConsultation;

    @Convert(converter = OnlinePlatformConverter.class)
    @Column(name = "online_platform")
    private OnlinePlatform onlinePlatform;

    @Column(name = "offline_address")
    private String offlineAddress;

    @Column(name = "client_request_for_therapy", columnDefinition = "TEXT")
    private String clientTherapyRequest;

    @Column(name = "meeting_time_day")
    private String meetingTimeDay;

    @Column(name = "financial_condition")
    private Integer financialCondition;

    @Column(name = "residence_address", columnDefinition = "TEXT")
    private String residenceAddress;

    @Column(name = "peer_recommendations")
    private String peerRecommendation;

    @Column(name = "special_terms_contact", columnDefinition = "TEXT")
    private String specialTermsContact;

    @Convert(converter = FamilyStatusConverter.class)
    @Column(name = "family_status")
    private FamilyStatus familyStatus;

    @Convert(converter = PriorityCommunicationChannelConverter.class)
    @Column(name = "priority_communication_channel")
    private PriorityCommunicationChannel priorityCommunicationChannel;

    @Column(name = "supervision_status_this_client")
    private boolean supervisionStatusThisClient;

    @Column(name = "name_and_contact_supervisor")
    private String contactSupervisor;

    @Column(name = "supervision_material", columnDefinition = "TEXT")
    private String supervisionMaterial;

    @Column(name = "taking_medic", columnDefinition = "TEXT")
    private String takingMedic;

    @Column(name = "prev_experience", columnDefinition = "TEXT")
    private String prevExperience;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public Customer(Person person) {
        this.id = person.getId();
        this.clientType = ClientType.CUSTOMER;
        this.fullName = person.getFullName();
        this.firstName = person.getFirstName();
        this.secondName = person.getSecondName();
        this.lastName = person.getLastName();
        this.birth = person.getBirth();
        this.phoneNumber = person.getPhoneNumber();
        this.mail = person.getMail();
        this.gender = person.getGender();
        this.clientStatus = person.getClientStatus();
        this.meetingFormat = person.getMeetingFormat();
        this.setApplicationFormStatus(person.getApplicationFormStatus());
        this.setPersonCredentials(new PersonCredentials(Set.of(new Role(3,"ROLE_CUSTOMER"))));
    }

    public Customer() {
        this.setPersonCredentials(new PersonCredentials(Set.of(new Role(3,"ROLE_CUSTOMER"))));
        this.setApplicationFormStatus(ApplicationFormStatus.NOT_CREATED);
    }
}

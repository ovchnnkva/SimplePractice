package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.company.understandablepractice.model.types.*;
import ru.company.understandablepractice.model.types.converters.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@ToString
@Getter
@Setter
@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
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

    @Convert(converter = ContactMethodConverter.class)
    @Column(name = "contact_method")
    protected ContactMethod contactMethod;

    @Column(name = "date_of_first_request")
    protected LocalDate dateFirstRequest;

    @Column(name = "date_of_first_consultation")
    protected LocalDate dateFirstConsultation;

    @Convert(converter = OnlinePlatformConverter.class)
    @Column(name = "online_platform")
    protected OnlinePlatform onlinePlatform;

    @Column(name = "offline_address")
    protected String offlineAddress;

    @Column(name = "client_request_for_therapy", columnDefinition = "TEXT")
    protected String clientTherapyRequest;

    @Column(name = "meeting_time_day")
    protected String meetingTimeDay;

    @Column(name = "financial_condition")
    protected Integer financialCondition;

    @Column(name = "residence_address", columnDefinition = "TEXT")
    protected String residenceAddress;

    @Column(name = "peer_recommendations")
    protected String peerRecommendation;

    @Column(name = "special_terms_contact", columnDefinition = "TEXT")
    protected String specialTermsContact;

    @Convert(converter = FamilyStatusConverter.class)
    @Column(name = "family_status")
    protected FamilyStatus familyStatus;

    @Convert(converter = PriorityCommunicationChannelConverter.class)
    @Column(name = "priority_communication_channel")
    protected PriorityCommunicationChannel priorityCommunicationChannel;

    @Column(name = "supervision_status_this_client")
    protected boolean supervisionStatusThisClient;

    @Column(name = "name_and_contact_supervisor")
    protected String contactSupervisor;

    @Column(name = "supervision_material", columnDefinition = "TEXT")
    protected String supervisionMaterial;

    @Column(name = "taking_medic", columnDefinition = "TEXT")
    private String takingMedic;

    @Column(name = "prev_experience", columnDefinition = "TEXT")
    private String prevExperience;

    @Column(name = "notes", columnDefinition = "TEXT")
    protected String notes;

    @OneToOne(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private CustomerCredentials customerCredentials = new CustomerCredentials(Set.of(new Role(3, "ROLE_CUSTOMER")));

    public Customer(long id) {
        this.id = id;
    }

    public Customer() {
        this.setApplicationFormStatus(ApplicationFormStatus.NOT_CREATED);
    }

    public void setCustomerCredentials(CustomerCredentials customerCredentials) {
        customerCredentials.setCustomer(this);
        this.customerCredentials = customerCredentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

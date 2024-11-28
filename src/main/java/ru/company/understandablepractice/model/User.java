package ru.company.understandablepractice.model;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "supscription_active")
    private boolean subscriptionActive;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "mail")
    private String mail;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "professional_activity_description", columnDefinition = "TEXT")
    private String professionalActivityDescription;

    @Column(name = "education", columnDefinition = "TEXT")
    private String education;

    @Column(name = "user_image", columnDefinition = "TEXT")
    private String userImage;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<UserDiplomas> userDiplomasList;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private UserCredentials userCredentials;


    public User(long id) {
        this.id = id;
    }

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

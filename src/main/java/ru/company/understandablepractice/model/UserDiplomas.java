package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_diplomas")
public class UserDiplomas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diploma_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "photo_diploma")
    private String photoDiploma;
}

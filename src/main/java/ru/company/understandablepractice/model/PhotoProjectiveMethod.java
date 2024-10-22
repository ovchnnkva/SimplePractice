package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "photo_projective_method")
public class PhotoProjectiveMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_projective_method_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projective_method_id")
    private ProjectiveMethod projectiveMethod;

    @Column(name = "photo_method")
    private String photoMethod;

    @Column(name = "date_create_photo")
    private LocalDate dateCreatePhoto;
}
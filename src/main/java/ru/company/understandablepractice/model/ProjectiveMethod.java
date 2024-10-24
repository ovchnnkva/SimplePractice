package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.Meet;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "projective_method")
public class ProjectiveMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projective_method_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meet_id")
    private Meet meet;

    @Column(name = "date_create_method")
    private LocalDate dateCreateMethod;
}

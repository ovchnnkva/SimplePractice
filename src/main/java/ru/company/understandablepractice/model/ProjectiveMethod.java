package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.company.understandablepractice.model.Meet;

import java.time.LocalDate;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "date_create_method")
    private LocalDate dateCreateMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_method_id")
    private TypeMethod typeMethod;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "projective_method_id", referencedColumnName = "projective_method_id")
    private List<PhotoProjectiveMethod> photoProjectiveMethods;
}

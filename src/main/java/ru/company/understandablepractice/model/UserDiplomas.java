package ru.company.understandablepractice.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_diplomas")
public class UserDiplomas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diploma_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "photo_diploma")
    private String photoDiploma;
}

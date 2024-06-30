package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MAN("Мужской"),
    WOMAN("Женский");

    private final String tittle;
}

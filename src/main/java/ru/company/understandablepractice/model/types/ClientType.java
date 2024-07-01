package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {
    ADULT("Ребенок"),
    CHILD("Взрослый"),
    PAIR("Пара");

    private final String tittle;
}
